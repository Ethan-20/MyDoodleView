package com.example.hellomission.utils

import android.graphics.Path
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

@Serializer(forClass = Path::class)
object PathSerializer : KSerializer<Path> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("android.graphics.Path", PrimitiveKind.STRING)


    override fun deserialize(decoder: Decoder): Path {
        //把字符串转成Path
        val pathAsString = decoder.decodeString()
        return convertStringToPath(pathAsString)
    }

    override fun serialize(encoder: Encoder, value: Path) {
        //将path 转化成可序列化的形式
        val pathAsString = convertPathToString(value)
        encoder.encodeString(pathAsString)
    }

    private fun convertPathToString(path: Path): String {
        //实现将path转换为字符串的逻辑，比如json
        return Json.encodeToString(path)
    }

    private fun convertStringToPath(pathAsString: String): Path {
        //实现将字符串转换回path的逻辑
        return Json.decodeFromString<Path>(pathAsString)
    }
}