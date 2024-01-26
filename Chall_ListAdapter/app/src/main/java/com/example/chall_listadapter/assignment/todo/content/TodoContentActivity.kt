package com.example.chall_listadapter.assignment.todo.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chall_listadapter.R
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.ActivityTodoContentBinding

class TodoContentActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TODO_MODEL = "extra_todo_model"

        fun newIntent(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java)
    }

    private val binding: ActivityTodoContentBinding by lazy {
        ActivityTodoContentBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        toolBar.setNavigationOnClickListener {
            finish()
        }

        submit.setOnClickListener {
            val intent = Intent().apply {
                val title = todoTitle.text.toString()
                val content = todoContent.text.toString()
                putExtra(
                    EXTRA_TODO_MODEL,
                    TodoModel(
                        title = title,
                        content = content
                    )
                )
            }

            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

}