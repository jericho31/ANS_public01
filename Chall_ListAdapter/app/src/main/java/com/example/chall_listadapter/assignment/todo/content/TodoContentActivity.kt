package com.example.chall_listadapter.assignment.todo.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.ActivityTodoContentBinding

class TodoContentActivity : AppCompatActivity() {

    companion object {

        const val EXTRA_TODO_MODEL = "extra_todo_model"
        const val EXTRA_TODO_ENTRY_TYPE = "extra_todo_entry_type"
        const val EXTRA_ITEM_POSITION = "extra_item_position"

        enum class TodoContentType {
            CREATE, UPDATE, DELETE
        }

        /** newIntent 함수에 필요한 데이터를 파라미터로 만들면 호출하는 쪽에서 인터페이스처럼 사용 */
        fun newIntentForCreate(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.CREATE)
        }

        fun newIntentForUpdate(
            context: Context,
            position: Int
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.UPDATE)
            putExtra(EXTRA_ITEM_POSITION, position)
        }
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

        btnCreate.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
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

        btnDelete.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    EXTRA_TODO_ENTRY_TYPE,
                    TodoContentType.DELETE
                )
                putExtra(EXTRA_ITEM_POSITION, intent.getIntExtra(EXTRA_ITEM_POSITION, -1))
            })
//            onBackPressedDispatcher.onBackPressed()
            finish()
        }

        btnUpdate.setOnClickListener {
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra(
                    EXTRA_TODO_ENTRY_TYPE,
                    TodoContentType.UPDATE
                )
                putExtra(EXTRA_ITEM_POSITION, intent.getIntExtra(EXTRA_ITEM_POSITION, -1))
                putExtra(
                    EXTRA_TODO_MODEL,
                    TodoModel(
                        title = todoTitle.text.toString(),
                        content = todoContent.text.toString()
                    )
                )
            })
            onBackPressedDispatcher.onBackPressed()
        }

        val contentType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            intent.getSerializableExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType::class.java)
        else
            @Suppress("DEPRECATION")
            intent.getSerializableExtra(EXTRA_TODO_ENTRY_TYPE) as? TodoContentType
        when (contentType) {
            null -> {}

            TodoContentType.CREATE -> {
                btnDelete.isVisible = false
                btnUpdate.isVisible = false
            }

            TodoContentType.UPDATE, TodoContentType.DELETE -> {
                btnCreate.isVisible = false
            }
        }
    }
}