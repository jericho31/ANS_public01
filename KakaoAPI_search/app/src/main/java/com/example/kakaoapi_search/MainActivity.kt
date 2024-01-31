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
        setFragment(imgSrcFrag)

        b.btnMainSearch.setOnClickListener {
            setFragment(imgSrcFrag)
        }
        b.btnMainMybox.setOnClickListener {
            setFragment(myboxFrag)
        }

//        imgSrcFrag.setAdapterOnItemClickListener(object : SearchListAdapter.OnItemClickListener {
//            override fun onItemClick(itemModel: ItemModel) {
//                addToSelected(itemModel)
//            }
//        })
        imgSrcFrag.setAdapterAddToSelected {
            addToSelected(it)
        }
        imgSrcFrag.setAdapterRemoveFromSelected {
            removeFromSelected(it)
        }
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(binding.flMain.id, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
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