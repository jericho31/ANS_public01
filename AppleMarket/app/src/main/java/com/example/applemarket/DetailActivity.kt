package com.example.applemarket

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
            intent.getParcelableExtra<PostingData>(Extra.item)
        } ?: PostingData(  // 만약 데이터를 안담아서 넘겼으면 기본값
            0, R.drawable.sample2, "title", "desc", "user",
            999999, "address", 999, 999
        )

        with(binding) {
            ivDetailProduct.setImageResource(data.resId)
            tvDetailUser.text = data.user
            tvDetailAddress.text = data.address
            tvDetailTitle.text = data.title
            tvDetailDesc.text = data.desc
            tvDetailPrice.text = "${DecimalFormat(",###").format(data.price)}원"

            ivDetailBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }
}