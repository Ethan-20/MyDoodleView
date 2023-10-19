package com.example.hellomission.utils

import android.content.Context
import android.util.Log
import com.example.hellomission.bean.DrawPathEntry
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.OutputStreamWriter


object DataUtil {
    val TAG ="DataUtil"
    //todo 保存文件到本地，需要返回处理结果
    fun saveDataToFile(context: Context, file:String, list:ArrayList<DrawPathEntry>):Boolean{
        try {
            context.filesDir
            val json = Json { prettyPrint = true }
            val jsonString = json.encodeToString(list)

            val fileOutputStream = context.openFileOutput(file, Context.MODE_PRIVATE)
            val writer = OutputStreamWriter(fileOutputStream)

            writer.use {
                it.write(jsonString)
            }
            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error saving data to file", e)
            return false
        }
    }

    fun loadPathFromFile(context: Context, file:String):ArrayList<DrawPathEntry>{
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
