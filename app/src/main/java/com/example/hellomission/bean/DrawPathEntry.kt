package com.example.hellomission.bean

import android.graphics.Paint
import android.graphics.Path
import com.example.hellomission.utils.PaintSerializer
import com.example.hellomission.utils.PathSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class DrawPathEntry (
    //paint需要设置序列化器?
    @Serializable
    val path:Path,
    val color:Int,
    val width:Float,
    val isEraser:Boolean
    )