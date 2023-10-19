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
import android.graphics.drawable.shapes.Shape
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.hellomission.base.DrawAction
import com.example.hellomission.bean.DrawPathEntry
import com.example.hellomission.bean.PaintColorType
import com.example.hellomission.bean.PaintWidthType
import com.example.hellomission.ui.action.LineAction
import com.example.hellomission.utils.ShapeConstant
import java.lang.ref.WeakReference
import kotlin.math.sqrt

class DoodleView constructor(context: Context,attrs:AttributeSet):View(context,attrs){

    private val TAG: String = "DoodleView"
    private lateinit var mCurrentPath :Path
    private lateinit var drawPathEntry: WeakReference<DrawPathEntry>
    private var action: DrawAction
    private var pathList = ArrayList<DrawPathEntry>()
    private val paint: Paint by lazy{ Paint() }
    private lateinit var paintCanvas:Canvas
    private lateinit var bitmap: Bitmap
    private val eraserPaint:Paint by lazy{ Paint() }
    private var isEraser = false
    private var isDrawing = false
    init {
        paint.isAntiAlias = true

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE

        eraserPaint.style = Paint.Style.STROKE;
        eraserPaint.strokeWidth = 100f
        eraserPaint.color = Color.TRANSPARENT
        eraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        //这里的action实例对象都需要设置成单例
        action = ActionManager.getAction(ShapeConstant.LINE)

    }
    private fun setBitmap(){
        bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888)
        paintCanvas = Canvas(bitmap)
    }

    fun setAction(shape:ShapeConstant){
        //防止在用橡皮擦时切换成除了线外其他图形
        if(isEraser&&shape!=ShapeConstant.LINE) return
        isEraser = false
        action = ActionManager.getAction(shape)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(TAG,"EventAction = ${event!!.action}")
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->
            {
                mCurrentPath = Path()
                drawPathEntry = WeakReference(DrawPathEntry(mCurrentPath,paint.color,paint.strokeWidth,isEraser))
                action.onDown(event.x,event.y,mCurrentPath)
            }

            MotionEvent.ACTION_MOVE ->{
                isDrawing = true
                action.onMove(event.x,event.y,mCurrentPath)
            }
            MotionEvent.ACTION_UP -> {
                action.onUp(event.x,event.y,mCurrentPath)
                if(isEraser) paintCanvas.drawPath(mCurrentPath,eraserPaint)
                else paintCanvas.drawPath(mCurrentPath,paint)
                if(isDrawing)
                {
                    drawPathEntry.get()?.let { pathList.add(it) }
                }
//                mCurrentPath.reset()
                isDrawing = false
            }
        }
        postInvalidate()
        return true
    }

    //原本onLayout()方法中调用bitmap修改到这里
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setBitmap()
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (!bitmap.isRecycled) {
            canvas.drawBitmap(bitmap,0f,0f,null)
        }
        if (isDrawing){
            if(isEraser) canvas.drawPath(mCurrentPath,eraserPaint)
            else canvas.drawPath(mCurrentPath,paint)
        }
    }

    //撤销操作
    fun withDraw(){
        if (pathList.isEmpty()) {
            return
        }
        pathList.removeLast()
        //把paintCanvas的bitmap变透明
        paintCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        val newPaint = Paint(paint)
        for (pathEntry in pathList) {
            if (pathEntry.isEraser){
                paintCanvas.drawPath(pathEntry.path,eraserPaint)
            }
            else{
                newPaint.color = pathEntry.color
                newPaint.strokeWidth = pathEntry.width
                paintCanvas.drawPath(pathEntry.path,newPaint)
            }
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
            PaintWidthType.SMALL ->{
                paint.strokeWidth = 2f
            }

            PaintWidthType.MIDDLE -> {
                paint.strokeWidth = 5f
            }
            PaintWidthType.LARGE -> {
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
                action = ActionManager.getAction(ShapeConstant.LINE)
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