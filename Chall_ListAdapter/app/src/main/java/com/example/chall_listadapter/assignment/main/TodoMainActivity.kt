package com.example.chall_listadapter.assignment.main

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.chall_listadapter.R
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.assignment.todo.content.TodoContentActivity
import com.example.chall_listadapter.assignment.todo.list.TodoListFragment
import com.example.chall_listadapter.databinding.ActivityTodoMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class TodoMainActivity : AppCompatActivity() {
    private val binding: ActivityTodoMainBinding by lazy {
        ActivityTodoMainBinding.inflate(layoutInflater)
    }

    private val viewPagerAdapter by lazy {
        TodoMainViewPagerAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() = with(binding) {
        viewPager.adapter = viewPagerAdapter
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (viewPagerAdapter.getFragment(position) is TodoListFragment) {
                    fabCreateTodo.show()
                } else {
                    fabCreateTodo.hide()
                }
            }
        })

        // TabLayout x ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()

        // fab
        val createTodoLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }

                val fragment = viewPagerAdapter.getTodoListFragment() as? TodoListFragment
                fragment?.addTodoItem(todoModel)
            }
        }
        fabCreateTodo.setOnClickListener {
            createTodoLauncher.launch(
                TodoContentActivity.newIntent(this@TodoMainActivity)
            )
        }
    }
}