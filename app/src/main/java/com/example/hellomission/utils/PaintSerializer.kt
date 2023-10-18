package com.example.hellomission.utils

import android.graphics.Paint
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

@Serializer(forClass = Paint::class)
object PaintSerializer :KSerializer<Paint>{
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("android.graphics.Paint",PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Paint {
        //把字符串转成paint
        val paintAsString = decoder.decodeString()
        return convertStringToPaint(paintAsString)
    }

    override fun serialize(encoder: Encoder, value: Paint) {
        //将paint 转化成字符串
        val paintAsString = convertPaintToString(value)
        encoder.encodeString(paintAsString)
    }

    private fun convertPaintToString(paint: Paint):String{
        //将path转换为字符串，比如json
        return Json.encodeToString(paint)
    }

    private fun convertStringToPaint(paintAsString:String): Paint {
        //将字符串转换回path
        return Json.decodeFromString<Paint>(paintAsString)
    }
}