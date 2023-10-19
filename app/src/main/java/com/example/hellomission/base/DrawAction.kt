package com.example.hellomission.base

import android.graphics.Path

abstract class DrawAction (){

    abstract fun onDown(x:Float,y:Float,mCurrentPath:Path)

    abstract fun onMove(x:Float,y:Float,mCurrentPath:Path)

    abstract fun onUp(x:Float,y:Float,mCurrentPath:Path)
}