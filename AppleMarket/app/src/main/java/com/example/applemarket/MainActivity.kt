package com.example.applemarket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAdapter(dataList)
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        /** 아이템 클릭 이벤트로 메인의 뷰의 값을 변경하는 함수를 작성해 인터페이스 변수에 집어넣기 */
        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val item = dataList[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // TODO: parcelize
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }
    }
}