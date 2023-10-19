package com.example.hellomission

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hellomission.bean.PaintColorType
import com.example.hellomission.bean.PaintWidthType
import com.example.hellomission.ui.DoodleView
import com.example.hellomission.ui.action.CircleAction
import com.example.hellomission.utils.DataUtil
import com.example.hellomission.utils.ShapeConstant

class DoodleActivity : AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doodle)
        initView()
        initListener()
    }

    fun initView(){
        doodleView = findViewById(R.id.doodle_view)
        smallButton = findViewById(R.id.btn_small)
        middleButton = findViewById(R.id.btn_middle)
        whiteButton = findViewById(R.id.btn_white)
        blackButton = findViewById(R.id.btn_balck)
        withDrawButton = findViewById(R.id.btn_withDraw)
        cleanButton = findViewById(R.id.btn_clean)
        rubberButton = findViewById(R.id.btn_rubber)
        saveButton = findViewById(R.id.btn_save)
        lineButton = findViewById(R.id.btn_line)
        circleButton = findViewById(R.id.btn_circle)
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
            DataUtil(this).savePathEntry(doodleView.getPathList())
        }
        lineButton.setOnClickListener{
            doodleView.setAction(ShapeConstant.LINE)
        }
        circleButton.setOnClickListener {
            doodleView.setAction(ShapeConstant.CIRCLE)
        }
    }

}