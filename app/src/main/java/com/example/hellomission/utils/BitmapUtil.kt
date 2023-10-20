package com.example.hellomission.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtil {

    //存储文件功能需要存储权限，自己的手机看不到保存的图片，不知道为什么
    fun generatePicture(context: Activity, bitmap: Bitmap){

            // 已经有权限，可以执行文件写入操作
            // 首先，保存Bitmap到文件
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "my_image.jpg")
            try {
                val fileOutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
                fileOutputStream.flush()
                fileOutputStream.close()

                // 其次，将保存的文件添加到相册中  --> 无效
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "My Image")
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image saved from my app")
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)

                val contentResolver = context.contentResolver
                contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                Toast.makeText(context, "Image saved to gallery", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(context, "Failed to save image", Toast.LENGTH_SHORT).show()
            }
    }
}