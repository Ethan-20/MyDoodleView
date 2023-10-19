package com.example.hellomission

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hellomission.bean.PaintColorType
import com.example.hellomission.bean.PaintWidthType
import com.example.hellomission.databinding.ActivityDoodleBinding
import com.example.hellomission.ui.DoodleView
import com.example.hellomission.utils.BitmapUtil
import com.example.hellomission.utils.ShapeConstant
import com.example.hellomission.viewmodel.DoodleViewModel

class DoodleActivity : AppCompatActivity() {
    private val TAG = "DoodleActivity"
    private lateinit var binding: ActivityDoodleBinding
    private lateinit var  doodleView: DoodleView
    private lateinit var smallButton : Button
    private lateinit var middleButton : Button
    private lateinit var whiteButton: Button
    private lateinit var blackButton : Button
    private lateinit var withDrawButton :Button
    private lateinit var cleanButton : Button
    private lateinit var rubberButton:Button
    private lateinit var saveButton:Button
    private lateinit var lineButton:Button
    private lateinit var circleButton:Button
    private lateinit var pictureButton:Button
    private lateinit var straightButton:Button
    private lateinit var recButton :Button
    private val viewModel: DoodleViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodle)
        initView()
        initListener()
    }

    fun initView(){
        binding = ActivityDoodleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        doodleView = binding.doodleView
        smallButton = binding.btnSmall
        middleButton = binding.btnMiddle
        whiteButton = binding.btnWhite
        blackButton = binding.btnBalck
        withDrawButton = binding.btnWithDraw
        cleanButton = binding.btnClean
        rubberButton = binding.btnRubber
        saveButton = binding.btnSave
        lineButton = binding.btnLine
        circleButton = binding.btnCircle
        pictureButton = binding.btnPicture
        straightButton = binding.btnStraight
        recButton = binding.btnRec
    }

    private fun initListener(){
        smallButton.setOnClickListener {
            doodleView.setPaintWidth(PaintWidthType.SMALL)
        }
        middleButton.setOnClickListener {
            doodleView.setPaintWidth(PaintWidthType.MIDDLE)
        }
        whiteButton.setOnClickListener {
            doodleView.setPaintColor(PaintColorType.WHITE)
        }
        blackButton.setOnClickListener {
            doodleView.setPaintColor(PaintColorType.BLACK)
        }
        withDrawButton.setOnClickListener {
            doodleView.withDraw()
        }
        cleanButton.setOnClickListener {
            doodleView.cleanDrawing()
        }
        rubberButton.setOnClickListener{
            doodleView.setPaintColor(PaintColorType.RUBBER)
        }
        saveButton.setOnClickListener{
            saveData()
        }
        lineButton.setOnClickListener{
            doodleView.setAction(ShapeConstant.LINE)
        }
        circleButton.setOnClickListener {
            doodleView.setAction(ShapeConstant.CIRCLE)
        }
        pictureButton.setOnClickListener{
            generatePicture()
        }
        straightButton.setOnClickListener {
            doodleView.setAction(ShapeConstant.STRAIGHT)
        }
        recButton.setOnClickListener {
            doodleView.setAction(ShapeConstant.RECTANGLE)
        }
    }

    override fun onResume() {
        super.onResume()
        loadPath()
    }

    override fun onStop() {
        super.onStop()
        viewModel.savePath(doodleView.getPathList())
    }

    fun generatePicture(){
        BitmapUtil.generatePicture(this,doodleView.getBitmap())
    }
    fun saveData(){
        //这里要发起一个activity询问是否保存在原文件夹或者另创文件
        //这里简化，只保留在原文件夹，返回处理结果
        //todo 这里是不是要考虑用异步？或者应该是同步？需要对result进行处理
        //todo 让用户输入新保存的文件名，需要加上存储路径前缀
        val newFile = "file1"
        val result = viewModel.saveData(true,newFile,this)
        Log.d(TAG,result)
    }

    //为view加载数据
    private fun loadPath() {
        //或者打开最后一次保存的文件，那么就需要将最后一个文件文件名保存在sp中,如果是第一次打开或者没有保存过呢？
        //查询sp如果没有记录，那么传入的file也找不到，抛出异常，DataUtil还是会返回笔迹列表对象，问题不大，就是解耦性好不好呢？
        //文件存放在私有文件夹里，不需要路径类
        val fileName = "default.json"
        val pathList = viewModel.loadFile(this,fileName)
        //pathList不会是空
        doodleView.loadPathList(pathList)
    }

}