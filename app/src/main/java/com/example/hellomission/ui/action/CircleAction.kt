package com.example.hellomission.ui.action

import android.graphics.Path
import com.example.hellomission.base.DrawAction
import kotlin.math.sqrt

class CircleAction:DrawAction() {

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f
    override fun onDown(x: Float, y: Float,mCurrentPath:Path) {
        startX = x
        startY = y
    }

    override fun onMove(x: Float, y: Float,mCurrentPath:Path) {
        mCurrentPath.reset()
        //圆心坐标，半径
        endX = x
        endY = y
        val x = (endX+startX)/2
        val y = (endY+startY)/2
        val num = (startX-x)*(startX-x)+(startY-y)*(startY-y)
        val radius = sqrt(num)
        mCurrentPath.addCircle(x,y,radius,Path.Direction.CW)
    }

    override fun onUp(x: Float, y: Float,mCurrentPath:Path) {
    }
}