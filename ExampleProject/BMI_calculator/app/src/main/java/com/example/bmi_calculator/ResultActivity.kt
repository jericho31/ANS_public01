package com.example.bmi_calculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.pow
import kotlin.math.round

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getIntExtra("height", 0)
        val weight = intent.getIntExtra("weight", 0)

        val bmi = round(weight / (height / 100.0).pow(2.0) * 10.0) / 10.0

        var resText = ""
        var resColor = 0
        var resImage = 0
        when {
            bmi < 18.5 -> {
                resText = "저체중"
                resColor = Color.YELLOW
                resImage = R.drawable.image_lv1
            }

            bmi < 23.0 -> {
                resText = "정상 체중"
                resColor = Color.GREEN
                resImage = R.drawable.image_lv2
            }

            bmi < 25.0 -> {
                resText = "과체중"
                resColor = Color.BLACK
                resImage = R.drawable.image_lv3
            }

            bmi < 30.0 -> {
                resText = "경도 비만"
                resColor = Color.CYAN
                resImage = R.drawable.image_lv4
            }

            bmi < 35.0 -> {
                resText = "중정도 비만"
                resColor = Color.MAGENTA
                resImage = R.drawable.image_lv5
            }

            else -> {
                resText = "고도 비만"
                resColor = Color.RED
                resImage = R.drawable.image_lv6
            }
        }

        val tvBMI = findViewById<TextView>(R.id.tv_resValue)
        val tvtext = findViewById<TextView>(R.id.tv_resText)
        val ivimage = findViewById<ImageView>(R.id.iv_image)
        val btnback = findViewById<Button>(R.id.btn_back)

        tvBMI.text = bmi.toString()
        tvtext.text = resText
        tvtext.setTextColor(resColor)
        ivimage.setImageResource(resImage)

        btnback.setOnClickListener {
            finish()
        }
    }
}
