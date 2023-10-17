package com.example.hellomission.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
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
    private val pathList = ArrayList<DrawPathEntry>()
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
        eraserPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)

        // It'd be impossible if there's no bitmap and canvas in this view
        //but I don't know the relationship between bitmap and canvas
        //therefore I'll fix it later
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        Log.d(TAG,"EventAction = ${event!!.action}")
        when(event!!.action){
            MotionEvent.ACTION_DOWN ->
            {
                startX = event.x
                startY = event.y
                mCurrentPath =  Path()
                mCurrentPath.moveTo(startX,startY)
                val newPaint = Paint(paint)
                pathList.add(DrawPathEntry(mCurrentPath,newPaint,isEraser))
            }

            MotionEvent.ACTION_MOVE ->{
                endX = event.x
                endY = event.y
                //绘制贝塞尔曲线
                mCurrentPath.quadTo(startX,startY,endX,endY)
                startX = endX
                startY = endY
                Log.d(TAG,"i'm moving")
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        postInvalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (drawPathEntry in pathList) {
            canvas.drawPath(drawPathEntry.path,drawPathEntry.paint)
        }
    }

    //撤销操作
    fun withDraw(){
        if (pathList.isNotEmpty()) {
            pathList.removeLast()
            postInvalidate()
        }
    }

    //清空画板
    fun cleanDrawing(){
        pathList.clear()
        postInvalidate()
    }

    //画笔粗细设置
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

    // 设置画笔颜色
    fun setPaintColor(type:PaintColorType){
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
        }
    }
}