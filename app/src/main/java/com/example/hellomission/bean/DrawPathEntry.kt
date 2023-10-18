package com.example.hellomission.bean

import android.graphics.Paint
import android.graphics.Path
import com.example.hellomission.utils.PaintSerializer
import com.example.hellomission.utils.PathSerializer
import kotlinx.serialization.Serializable

@Serializable
data class DrawPathEntry (
    @Serializable(with = PathSerializer::class)
    val path: Path,
    @Serializable(with = PaintSerializer::class)
    val paint:Paint)