package com.example.kakaoapi_search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.kakaoapi_search.data.AppContainer
import com.example.kakaoapi_search.data.DefaultAppContainer
import com.example.kakaoapi_search.databinding.ActivityMainBinding

/** AppContainer instance used by the rest of classes to obtain dependencies */
val myContainer: AppContainer = DefaultAppContainer()

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val imgSrcFrag = ImageSearchFragment.newInstance()
    private val myboxFrag = MyboxFragment.newInstance()

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
    }

    private fun setFragment(frag: Fragment) {
        supportFragmentManager.commit {
            replace(binding.flMain.id, frag)
            setReorderingAllowed(true)
            addToBackStack("")
        }
    }
}