package com.example.applemarket

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

        val data = dataList[intent.getIntExtra("position", 0)]

        with(binding) {
            ivDetailProduct.setImageResource(data.resId)
            tvDetailUser.text = data.user
            tvDetailAddress.text = data.address
            tvDetailTitle.text = data.title
            tvDetailDesc.text = data.desc
            tvDetailPrice.text = "${DecimalFormat(",###").format(data.price)}원"

            ivDetailBack.setOnClickListener { onBackPressed() }
        }
    }
}