package com.example.hellomission

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.hellomission.bean.PaintColorType
import com.example.hellomission.bean.PaintWidthType
import com.example.hellomission.ui.DoodleView

class DoodleActivity : AppCompatActivity() {
    private lateinit var  doodleView: DoodleView
    private lateinit var smallButton : Button
    private lateinit var middleButton : Button
    private lateinit var whiteButton: Button
    private lateinit var blackButton : Button
    private lateinit var withDrawButton :Button
    private lateinit var cleanButton : Button

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
    }
    private fun initListener(){
        smallButton.setOnClickListener {
            doodleView.setPaintWidth(PaintWidthType.small)
        }
        middleButton.setOnClickListener {
            doodleView.setPaintWidth(PaintWidthType.middle)
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
    }

}