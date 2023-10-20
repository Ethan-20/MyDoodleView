package com.example.hellomission.ui.action

import android.graphics.Path
import com.example.hellomission.base.DrawAction

class LineAction : DrawAction() {

    override fun onDown(x: Float, y: Float, mCurrentPath: Path) {

        mCurrentPath.moveTo(x, y)
    }

    override fun onMove(x: Float, y: Float, mCurrentPath: Path) {

        mCurrentPath.lineTo(x, y)
    }

    override fun onUp(x: Float, y: Float, mCurrentPath: Path) {

    }

}