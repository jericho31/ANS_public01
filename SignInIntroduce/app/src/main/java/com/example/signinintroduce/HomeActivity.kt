package com.example.signinintroduce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

/** 랜덤으로 띄울 때 이전에 띄웠던 프로필이랑 다른 거 띄우기 위한 전역 변수 */
var randomNumber = -1

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val dataId = intent.getStringExtra("id")
        val ivHome = findViewById<ImageView>(R.id.iv_home)
        val tvId = findViewById<TextView>(R.id.tv_home_id)
        val tvName = findViewById<TextView>(R.id.tv_home_name)
        val tvAge = findViewById<TextView>(R.id.tv_home_age)
        val tvMbti = findViewById<TextView>(R.id.tv_home_mbti)
        tvId.text = "아이디: $dataId"
        when (dataId) {
            "admin" -> {}
            "spa001" -> {
                ivHome.setImageResource(R.drawable.rtan)
                tvName.text = "이름: 스파르타코딩이"
                tvAge.text = "나이: 23"
                tvMbti.text = "MBTI: ESTJ"
            }

            else -> {
                val before = randomNumber
                do randomNumber = Random.nextInt(5) while (randomNumber == before)
                when (randomNumber) {
                    0 -> {
                        ivHome.setImageResource(R.drawable.face1)
                        tvName.text = "이름: 잘생이"
                        tvAge.text = "나이: 28"
                        tvMbti.text = "MBIT: ESFP"
                    }

                    1 -> {
                        ivHome.setImageResource(R.drawable.face2)
                        tvName.text = "이름: 평범이"
                        tvAge.text = "나이: 24"
                        tvMbti.text = "MBIT: INFP"
                    }

                    2 -> {
                        ivHome.setImageResource(R.drawable.face3)
                        tvName.text = "이름: 곱슬이"
                        tvAge.text = "나이: 29"
                        tvMbti.text = "MBIT: ENTJ"
                    }

                    3 -> {
                        ivHome.setImageResource(R.drawable.face4)
                        tvName.text = "이름: 털보"
                        tvAge.text = "나이: 20"
                        tvMbti.text = "MBIT: INTP"
                    }

                    else -> {
                        ivHome.setImageResource(R.drawable.face5)
                        tvName.text = "이름: 활발이"
                        tvAge.text = "나이: 25"
                        tvMbti.text = "MBIT: ENFP"
                    }
                }
            }
        }

        val btn = findViewById<Button>(R.id.btn_home_finish)
        btn.setOnClickListener {
            finish()
        }
    }
}