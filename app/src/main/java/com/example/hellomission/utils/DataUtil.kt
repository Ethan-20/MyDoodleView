package com.example.hellomission.utils

import android.content.Context
import android.content.res.AssetManager
import android.util.Log
import com.example.hellomission.bean.DrawPathEntry
import fr.arnaudguyon.xmltojsonlib.JsonToXml
import fr.arnaudguyon.xmltojsonlib.XmlToJson
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


final class DataUtil(context: Context) {
    val TAG ="Datautil"
    val xmlFile = "generatedXML.xml"
//    val xml = xmlFile.readText()
    private val mContext:Context = context
    companion object{

    }
    fun savePathEntry(list:ArrayList<DrawPathEntry>){
        //把list的节点保存到xml中
            val entryAsJson = Json.encodeToString(list)
            val jsonToXml = JsonToXml.Builder(entryAsJson).build()
            val xmlString = jsonToXml.toString()
            Log.d(TAG,xmlString)
            generateXML(xmlString)
    }

    //生成xml文件
    fun generateXML(xmlString:String){
        Log.d(TAG,"generating xml")
    }

    //从xml文件中读取转成json,再转成list
    fun parseXML():ArrayList<DrawPathEntry>{
        val assetManager: AssetManager = mContext.getAssets()
        val inputStream = assetManager.open(xmlFile)
        val xmlToJson = XmlToJson.Builder(inputStream, null).build()
        val jsonString = xmlToJson.toString()
        //获取到pathlist
        val list = Json.decodeFromString<ArrayList<DrawPathEntry>>(jsonString)
        inputStream.close()
        return list
    }


}
