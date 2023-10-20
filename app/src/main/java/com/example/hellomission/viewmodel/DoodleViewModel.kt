package com.example.hellomission.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.hellomission.bean.DrawPathEntry
import com.example.hellomission.utils.DataUtil

class DoodleViewModel : ViewModel() {

    //设置文件来源，保存文件需要判断是否覆盖原文件
    private lateinit var sourceFile: String

    private lateinit var pathList: ArrayList<DrawPathEntry>

    init {

    }

    fun savePath(list: ArrayList<DrawPathEntry>) {
        this.pathList = list
    }

    //保存数据到本地，返回处理结果
    fun saveData(saveInSource: Boolean, newFile: String, context: Context): String {
        val file: String = if (saveInSource) sourceFile else newFile
//        val file = newFile
        val isSuccess = DataUtil.saveDataToFile(context, file, pathList)
        return if (isSuccess) "文件保存成功，文件名为$file" else "文件保存失败，请稍后重试"
    }

    //从本地加载数据，返回加载到的数据
    fun loadPath(): ArrayList<DrawPathEntry> {
        pathList.let { return pathList }
    }

    fun loadFile(context: Context, file: String): ArrayList<DrawPathEntry> {
        this.pathList = DataUtil.loadPathFromFile(context, file)
        return pathList
    }

    //设置文件来源
    fun setSourceFile(file: String) {
        sourceFile = file
    }

}