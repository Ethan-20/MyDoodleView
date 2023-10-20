package com.example.hellomission.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import android.util.Log
import com.example.hellomission.bean.DrawPathEntry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter


object DataUtil {
    val TAG = "DataUtil"

    //todo 保存文件到本地，需要返回处理结果
    fun saveDataToFile(
        context: Context,
        fileName: String,
        list: ArrayList<DrawPathEntry>
    ): Boolean {
        try {
            context.filesDir
            val json = Json { prettyPrint = true }
            val jsonString = json.encodeToString(list)
            //文件名，需要写入数据
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)
            val fileOutputStream = FileOutputStream(file)
            val writer = OutputStreamWriter(fileOutputStream)
            writer.use {
                it.write(jsonString)
            }
            fileOutputStream.flush()
            fileOutputStream.close()
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error saving data to file", e)
            return false
        }
    }

    fun loadPathFromFile(context: Context, file: String): ArrayList<DrawPathEntry> {
        try {
            val fileInputStream = context.openFileInput(file)
            val json = Json { prettyPrint = true }
            val jsonString = fileInputStream.reader().use { it.readText() }

            return json.decodeFromString(jsonString)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading data from file", e)
            return ArrayList()
        }
    }

}
