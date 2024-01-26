package com.example.chall_listadapter.assignment.todo.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.ActivityTodoContentBinding

class TodoContentActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TODO_MODEL = "extra_todo_model"

        /** newIntent 함수에 필요한 데이터를 파라미터로 만들면 호출하는 쪽에서 인터페이스처럼 사용 */
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
            onBackPressedDispatcher.onBackPressed()
//            finish()
        }

        submit.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().also {
                it.putExtra(
                    EXTRA_TODO_MODEL,
                    TodoModel(
                        title = todoTitle.text.toString(),
                        content = todoContent.text.toString()
                    )
                )
            })
            onBackPressedDispatcher.onBackPressed()
//            finish()
        }
    }
}