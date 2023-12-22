package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.random.Random

/** 랜덤으로 띄울 때 이전에 띄웠던 프로필이랑 다른 거 띄우기 위한 전역 변수 */
var randomNumber = -1

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val dataId = intent.getStringExtra(Extra.id)
        val ivHome = findViewById<ImageView>(R.id.iv_home)
        val tvId = findViewById<TextView>(R.id.tv_home_id)
        val tvName = findViewById<TextView>(R.id.tv_home_name)
        val tvAge = findViewById<TextView>(R.id.tv_home_age)
        val tvMbti = findViewById<TextView>(R.id.tv_home_mbti)
        tvId.text = getString(R.string.home_id_format, dataId)
        when (dataId) {
            "admin" -> {}
            "spa001" -> {
                ivHome.setImageResource(R.drawable.rtan)
                tvName.text = getString(R.string.home_name_format, "스파르타코딩이")
                tvAge.text = getString(R.string.home_age_format, "23")
                tvMbti.text = getString(R.string.home_mbti_format, "ESTJ")
            }

            else -> {
                val before = randomNumber
                do randomNumber = Random.nextInt(5) while (randomNumber == before)
                when (randomNumber) {
                    0 -> {
                        ivHome.setImageResource(R.drawable.face1)
                        tvName.text = getString(R.string.home_name_format, "잘생이")
                        tvAge.text = getString(R.string.home_age_format, "28")
                        tvMbti.text = getString(R.string.home_mbti_format, "ESFP")
                    }

                    1 -> {
                        ivHome.setImageResource(R.drawable.face2)
                        tvName.text = getString(R.string.home_name_format, "평범이")
                        tvAge.text = getString(R.string.home_age_format, "24")
                        tvMbti.text = getString(R.string.home_mbti_format, "INFP")
                    }

                    2 -> {
                        ivHome.setImageResource(R.drawable.face3)
                        tvName.text = getString(R.string.home_name_format, "곱슬이")
                        tvAge.text = getString(R.string.home_age_format, "29")
                        tvMbti.text = getString(R.string.home_mbti_format, "ENTJ")
                    }

                    3 -> {
                        ivHome.setImageResource(R.drawable.face4)
                        tvName.text = getString(R.string.home_name_format, "털보")
                        tvAge.text = getString(R.string.home_age_format, "20")
                        tvMbti.text = getString(R.string.home_mbti_format, "INTP")
                    }

                    else -> {
                        ivHome.setImageResource(R.drawable.face5)
                        tvName.text = getString(R.string.home_name_format, "활발이")
                        tvAge.text = getString(R.string.home_age_format, "25")
                        tvMbti.text = getString(R.string.home_mbti_format, "ENFP")
                    }
                }
            }
        }

        val btn = findViewById<ConstraintLayout>(R.id.btn_home_finish_selector)
        btn.setOnClickListener {
            finish()
        }
    }
}