package com.example.hellomission.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Xfermode
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.hellomission.bean.DrawPathEntry
import com.example.hellomission.bean.PaintColorType
import com.example.hellomission.bean.PaintWidthType

class DoodleView constructor(context: Context,attrs:AttributeSet):View(context,attrs){
    private val TAG: String = "DoodleView"
    private val paint: Paint = Paint()
    private val eraserPaint:Paint = Paint()
    private var pathList = ArrayList<DrawPathEntry>()
    private lateinit var bitmap:Bitmap
    private lateinit var paintCanvas:Canvas
    private var isEraser = false
    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f
    private var mCurrentPath:Path = Path()
    init {
        //anti Alias
        paint.isAntiAlias = true

        //paint using default style, add later maybe
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        // make eraser functional
        eraserPaint.style = Paint.Style.STROKE;
        eraserPaint.strokeWidth = 100f
        eraserPaint.color = Color.TRANSPARENT;
        eraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        // It'd be impossible if there's no bitmap and canvas in this view
        //but I don't know the relationship between bitmap and canvas
        //therefore I'll fix it later
    }
    fun setBitmap(){
        bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        paintCanvas = Canvas(bitmap)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(TAG,"EventAction = ${event!!.action}")
        val newPaint = if(isEraser) eraserPaint else Paint(paint)
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->
            {
                startX = event.x
                startY = event.y
                mCurrentPath =  Path()
                mCurrentPath.moveTo(startX,startY)
            }

            MotionEvent.ACTION_MOVE ->{
                endX = event.x
                endY = event.y
                //绘制贝塞尔曲线
                mCurrentPath.quadTo(startX,startY,endX,endY)
                paintCanvas.drawPath(mCurrentPath,newPaint)
                startX = endX
                startY = endY
                Log.d(TAG,"i'm moving")
            }
            MotionEvent.ACTION_UP -> {
                pathList.add(DrawPathEntry(mCurrentPath, newPaint))
            }
        }
        postInvalidate()
        return true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setBitmap()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!bitmap.isRecycled) {
            canvas.drawBitmap(bitmap,0f,0f,null)
        }

    }

    //撤销操作
    fun withDraw(){
        if (pathList.isEmpty()) {
            return
        }
        pathList.removeLast()
        //这行代码是把整个canvas变透明
        paintCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
        for (entry in pathList) {
            paintCanvas.drawPath(entry.path,entry.paint)
        }
        postInvalidate()
    }

    //清空画板
    fun cleanDrawing(){
        paintCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
        pathList.clear()
        postInvalidate()
    }

    //设置画笔粗细
    fun setPaintWidth(type:PaintWidthType){
        when(type){
            PaintWidthType.small ->{
                paint.strokeWidth = 2f
            }

            PaintWidthType.middle -> {
                paint.strokeWidth = 5f
            }
            PaintWidthType.large -> {
                paint.strokeWidth = 10f
            }
        }
    }

    // 设置画笔类型
    fun setPaintColor(type:PaintColorType){
        isEraser = false
        when(type){
            PaintColorType.RED -> {
                paint.color = Color.RED
            }
            PaintColorType.GREEN -> {
                paint.color = Color.GREEN
            }
            PaintColorType.WHITE -> {
                paint.color = Color.WHITE
            }
            PaintColorType.BLACK -> {
                paint.color = Color.BLACK
            }
            PaintColorType.YELLOW -> {
                paint.color = Color.YELLOW
            }
            PaintColorType.BLUE -> {
                paint.color = Color.BLUE
            }
            PaintColorType.RUBBER -> {
                isEraser = true
            }
        }
    }

    //提供笔迹给工具类
    fun getPathList():ArrayList<DrawPathEntry>{
        return pathList
    }
    //从xml文件中恢复笔迹
    fun loadPathList(list:ArrayList<DrawPathEntry>){
        this.pathList = list
    }
}