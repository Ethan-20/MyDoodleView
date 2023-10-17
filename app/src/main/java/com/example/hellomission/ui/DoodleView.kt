package com.example.hellomission.ui

import android.content.Context
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

class DoodleView constructor(context: Context,attrs:AttributeSet):View(context,attrs){
    private val TAG: String = "DoodleView"
    private val paint: Paint = Paint()
    private val eraserPaint:Paint = Paint()
    private val pathList = ArrayList<DrawPathEntry>()
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
                pathList.add(DrawPathEntry(mCurrentPath,paint,isEraser))
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
}