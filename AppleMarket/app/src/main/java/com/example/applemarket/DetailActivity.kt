package com.example.applemarket

import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applemarket.databinding.ActivityDetailBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Extra.item, PostingData::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(Extra.item)  // explicit 하다면서 타입 제거 가능하네?
        } ?: PostingData(  // 만약 데이터를 안담아서 넘겼으면 기본값
            0, R.drawable.sample2, "title", "desc", "user",
            999999, "address", 999, 999
        )

        binding.apply {
            ivDetailProduct.setImageResource(data.resId)
            tvDetailUser.text = data.user
            tvDetailAddress.text = data.address
            tvDetailTitle.text = data.title
            tvDetailDesc.text = data.desc
            // 문자열 합치지 말라고 떠서 바꿔줌
            "${DecimalFormat(",###").format(data.price)}원".also { tvDetailPrice.text = it }
            if (data.liked) ivDetailLike.setImageResource(R.drawable.heart_red)
            else ivDetailLike.setImageResource(R.drawable.heart_empty)
            tvDetailDegreeText.paintFlags = Paint.UNDERLINE_TEXT_FLAG

            ivDetailBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            ivDetailLike.setOnClickListener { likeOnClick(data) }
        }
    }

    private fun likeOnClick(data: PostingData) {
        dataList.firstOrNull { it.id == data.id }?.also {
            if (it.liked) {
                binding.ivDetailLike.setImageResource(R.drawable.heart_empty)
                it.liked = false
                it.like--
            } else {
                binding.ivDetailLike.setImageResource(R.drawable.heart_red)
                it.liked = true
                it.like++
            }
        }
    }
}