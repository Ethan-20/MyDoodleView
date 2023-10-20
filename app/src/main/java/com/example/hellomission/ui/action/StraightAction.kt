package com.example.hellomission.ui.action

import android.graphics.Path
import com.example.hellomission.base.DrawAction

class StraightAction : DrawAction() {
    private var startX = 0f
    private var startY = 0f

    override fun onDown(x: Float, y: Float, mCurrentPath: Path) {
        startX = x
        startY = y
        mCurrentPath.moveTo(startX, startY)
    }

    override fun onMove(x: Float, y: Float, mCurrentPath: Path) {
        mCurrentPath.reset()
        mCurrentPath.moveTo(startX, startY)
        mCurrentPath.lineTo(x, y)
    }

    override fun onUp(x: Float, y: Float, mCurrentPath: Path) {
    }
}