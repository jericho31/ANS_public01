package com.example.applemarket

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    private val fadeIn = AlphaAnimation(0.0f, 1.0f).apply { duration = 512L }
    private val fadeOut = AlphaAnimation(1.0f, 0.0f).apply { duration = 512L }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MyAdapter(dataList)
        binding.rv.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        binding.rv.adapter = adapter
        binding.rv.layoutManager = LinearLayoutManager(this)

        /** 어댑터의 아이템 클릭 이벤트 함수를 메인에서 작성해 인터페이스 변수에 집어넣기 */
        adapter.itemClick = object : MyAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(Extra.item, dataList[position])
                startActivity(intent)
            }

            override fun onLongClick(view: View?, position: Int): Boolean {
                deleteDialog(position)
                return true
            }
        }

        // TODO: "Deprecated in Java"라는 것은 코틀린에서는 계속 써도 괜찮다는 걸까요?
        // TODO: dispatcher를 쓰면, api 버전이 낮은 기종에서는 앱이 안돌아가지 않을까요? api 몇부터 호환되는지 어떻게 확인하나요?
        onBackPressedDispatcher.addCallback { exitDialog(this) }

        binding.ivMainBell.setOnClickListener { notification() }
        binding.fab.setOnClickListener { binding.rv.smoothScrollToPosition(0) }
        binding.rv.addOnScrollListener(createRvOnScrollListener())
    }

    override fun onResume() {
        // 상세페이지에서 좋아요 온오프하고 둘아오면 rv에 반영하기 위해.
        // TODO: notify를 더 구체적으로
        adapter.notifyDataSetChanged()
        super.onResume()
    }

//    // depreated 돼서 onBackPressedDispatcher로 작성함
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

    private fun createRvOnScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
            val fab = binding.fab
            if (rv.canScrollVertically(-1) && fab.isVisible.not()) {
                fab.isVisible = true
                fab.startAnimation(fadeIn)
            } else if (rv.canScrollVertically(-1).not() && fab.isVisible) {
                fab.startAnimation(fadeOut)
                fab.isVisible = false
            }
        }
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

        // 다이얼로그 뒷 배경이 어두워지지 않던 문제
        // 에뮬레이터의 개발자 옵션 - ‘HW 오버레이 사용 안함’ 활성화
    }

    private fun deleteDialog(position: Int) {
        AlertDialog.Builder(this).apply {
            setTitle("상품 삭제")
            setMessage("상품을 정말로 삭제하시겠습니까?")
            setIcon(R.drawable.chat)

            setNegativeButton("취소", null)
            setPositiveButton("확인") { _, _ ->
                dataList.removeAt(position)
                adapter.notifyItemRemoved(position)
                adapter.notifyItemRangeChanged(position, dataList.size)
            }
        }.show()
    }

    private fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder

        // 안드 8.0 Oreo 이상부터는 알림 채널을 먼저 만들어야 한다
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  // Oreo, api26
            val channelId = "one-channel"
            manager.createNotificationChannel(
                NotificationChannel(
                    channelId, "My Channel One", NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    description = "My Channel One Description"
                    setShowBadge(true)
                    val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)!!
                    val audioAttributes = AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .build()
                    setSound(uri, audioAttributes)
                    enableVibration(true)
                })
            builder = NotificationCompat.Builder(this, channelId)
        } else {  // < Oreo, api26
            @Suppress("DEPRECATION")
            builder = NotificationCompat.Builder(this)
        }

        // 알림에 띄울 이미지 비트맵이랑 실행시킬 인텐트 준비.
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.image_lv2)

        // 알림 정보
        // run 대신 apply 해도?
        builder.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)  // hide largeIcon while expanding
            )
            setLargeIcon(bitmap)
        }

        manager.notify(11, builder.build())
    }
}