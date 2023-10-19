package com.example.hellomission.ui.action

import android.graphics.Path
import com.example.hellomission.base.DrawAction

class LineAction : DrawAction() {
    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f
    override fun onDown(x: Float, y: Float,mCurrentPath:Path) {
        startX = x
        startY = y
        mCurrentPath.moveTo(x,y)
    }

    override fun onMove(x: Float, y: Float,mCurrentPath:Path) {
//        mCurrentPath.quadTo(startX,startY,x,y)
//        startX = x
//        startY = y
        mCurrentPath.lineTo(x,y)
    }

    override fun onUp(x: Float, y: Float,mCurrentPath:Path) {

    }

}