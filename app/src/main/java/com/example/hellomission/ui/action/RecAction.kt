package com.example.hellomission.ui.action

import android.graphics.Path
import com.example.hellomission.base.DrawAction

class RecAction :DrawAction(){
    private var startX = 0f
    private var startY = 0f
//    var left = 0f
//    var top = 0f
//    var right = 0f
//    var bottom = 0f

    override fun onDown(x: Float, y: Float, mCurrentPath: Path) {
        startX = x
        startY = y
        mCurrentPath.moveTo(startX,startY)
    }

    override fun onMove(x: Float, y: Float, mCurrentPath: Path) {
        //计算左右上下
        val left = minOf(x,startX)
        val right = maxOf(x,startX)
        val top = minOf(y,startY)
        val bottom = maxOf(y,startY)
        mCurrentPath.reset()
        mCurrentPath.addRect(left,top,right,bottom,Path.Direction.CW)
    }

    override fun onUp(x: Float, y: Float, mCurrentPath: Path) {
    }
}