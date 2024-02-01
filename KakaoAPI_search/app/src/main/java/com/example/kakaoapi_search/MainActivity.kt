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
        switchFragment(imgSrcFrag)
        b.btnMainSearch.setOnClickListener {
            switchFragment(imgSrcFrag)
//            imgSrcFrag.update()
        }
        b.btnMainMybox.setOnClickListener {
            switchFragment(myboxFrag)
//            myboxFrag.update()
        }

        // 뷰홀더 아이템클릭에서 메인액티비티의 '선택된 이미지 리스트'에 접근하기 위한 람다식 전달
        imgSrcFrag.setAdapterAddToSelected { addToSelected(it) }
        imgSrcFrag.setAdapterRemoveFromSelected { removeFromSelected(it) }
    }

    // 프래그먼트가 계속 새로 생성돼서 변경...인데 갱신 안되는 문제를 아직 못잡아서 일단 사용
    private fun switchFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(binding.flMain.id, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }

//    private fun switchFragment(fragment: Fragment) = supportFragmentManager.commit {
//        supportFragmentManager.fragments.forEach { hide(it) }
//        if (fragment.isAdded) show(fragment)
//        else add(binding.flMain.id, fragment)
//    }

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