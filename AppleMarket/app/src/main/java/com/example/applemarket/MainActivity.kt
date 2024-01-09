package com.example.applemarket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = MyAdapter(dataList)
        binding.rv.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        /** 아이템 클릭 이벤트로 메인의 뷰의 값을 변경하는 함수를 작성해 인터페이스 변수에 집어넣기 */
        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
//                val item = dataList[position]
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                // TODO: parcelize
                intent.putExtra("position", position)
                startActivity(intent)
            }
        }

        // TODO: "Deprecated in Java"라는 것은 코틀린에서는 계속 써도 괜찮다는 걸까요?
        // TODO: dispatcher를 쓰면, api 버전이 낮은 기종에서는 앱이 안돌아가지 않을까요? api 몇부터 호환되는지 어떻게 확인하나요?
        onBackPressedDispatcher.addCallback { exitDialog(this) }
    }

    private fun exitDialog(callback: OnBackPressedCallback) {
        AlertDialog.Builder(this).apply {
            setTitle("종료")
            setMessage("정말 종료하시겠습니까?")
            setIcon(R.drawable.chat)

            setNegativeButton("취소", null)
            setPositiveButton("확인") { _, _ ->
                callback.isEnabled = false
                onBackPressedDispatcher.onBackPressed()
            }
        }.show()
    }

//    @Deprecated("Deprecated in Java")
//    override fun onBackPressed() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("종료")
//        builder.setMessage("정말 종료하시겠습니까?")
//        builder.setIcon(R.drawable.chat)
//
//        val listener = DialogInterface.OnClickListener { _, _ -> super.onBackPressed() }
//
//        builder.setNegativeButton("취소", null)
//        builder.setPositiveButton("확인", listener)
//
//        builder.show()
//    }
}