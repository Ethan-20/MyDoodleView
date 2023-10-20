package com.example.hellomission.bean

import android.graphics.Path
import com.example.hellomission.utils.PathSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DrawPathEntry(
    //paint需要设置序列化器?
    @Serializable(with = PathSerializer::class)
    val path: Path,
    val color: Int,
    val width: Float,
    val isEraser: Boolean
)