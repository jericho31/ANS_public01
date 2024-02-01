package com.example.kakaoapi_search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.kakaoapi_search.data.AppContainer
import com.example.kakaoapi_search.data.DefaultAppContainer
import com.example.kakaoapi_search.databinding.ActivityMainBinding
import com.example.kakaoapi_search.imagesearch.ImageSearchFragment
import com.example.kakaoapi_search.model.ItemModel
import com.example.kakaoapi_search.mybox.MyboxFragment

/** AppContainer instance used by the rest of classes to obtain dependencies */
val myContainer: AppContainer = DefaultAppContainer()

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val selectedItems = mutableListOf<ItemModel>()

    private val imgSrcFrag = ImageSearchFragment.newInstance()
    private val myboxFrag = MyboxFragment.newInstance(selectedItems)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView(this)
    }

    private fun initView(ac: AppCompatActivity) = binding.also { b ->
        supportFragmentManager.commit {
            add(binding.flMain.id, imgSrcFrag)
            add(binding.flMain.id, myboxFrag)
            hide(myboxFrag)
        }
        b.btnMainSearch.setOnClickListener {
//            imgSrcFrag.update()
            switchFragment(imgSrcFrag)
        }
        b.btnMainMybox.setOnClickListener {
            myboxFrag.update()
            switchFragment(myboxFrag)
        }

        // 뷰홀더 아이템클릭에서 메인액티비티의 '선택된 이미지 리스트'에 접근하기 위한 람다식 전달
        imgSrcFrag.setAdapterAddToSelected { addToSelected(it) }
        imgSrcFrag.setAdapterRemoveFromSelected { removeFromSelected(it) }
    }

//    // 프래그먼트가 계속 새로 생성돼서 변경
//    private fun switchFragment(frag: Fragment) {
//        supportFragmentManager.commit {
//            replace(binding.flMain.id, frag)
//            setReorderingAllowed(true)
//            addToBackStack("")
//        }
//    }

    // add는 따로 다 해두고 전환할 때는 hide, show만 하자. 프래그먼트가 계속 추가되는 게 아니라서 확인 작업 불필요.
    // (그리고 처음 update 호출할 때 commit은 비동기인가 해서 binding이 null이라고 터지는 문제도 해결할 겸)
    private fun switchFragment(fragment: Fragment) = supportFragmentManager.commit {
        supportFragmentManager.fragments.forEach { hide(it) }
        show(fragment)
    }

    fun addToSelected(item: ItemModel) = selectedItems.add(item)
    fun removeAtFromSelected(pos: Int) = selectedItems.removeAt(pos)
    fun removeFromSelected(item: ItemModel) = selectedItems.remove(item)
    fun removeFromSelected(id: String): Boolean {
        val pos = selectedItems.indexOfFirst { it.id == id }
        if (pos >= 0) {
            selectedItems.removeAt(pos)
            return true
        }
        return false
    }
}