package com.example.chall_listadapter.assignment.todo.list

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.chall_listadapter.assignment.main.SharedViewModel
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.assignment.todo.content.TodoContentActivity
import com.example.chall_listadapter.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private var _binding: FragmentTodoListBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: TodoListViewModel by viewModels()

    private val updateTodoLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("myTag", "런처 콟백 resultCode: ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK) {
            val contentType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getSerializableExtra(
                    TodoContentActivity.EXTRA_TODO_ENTRY_TYPE,
                    TodoContentActivity.Companion.TodoContentType::class.java
                )
            } else {
                @Suppress("DEPRECATION")
                result.data?.getSerializableExtra(
                    TodoContentActivity.EXTRA_TODO_ENTRY_TYPE
                ) as? TodoContentActivity.Companion.TodoContentType
            }

            val position = result.data?.getIntExtra(
                TodoContentActivity.EXTRA_ITEM_POSITION, -1
            ) ?: -1

            Log.d("myTag", "런처 콟백 타입: $contentType, 포지션: $position")
            when (contentType) {
                null -> {}
                TodoContentActivity.Companion.TodoContentType.CREATE -> {}

                TodoContentActivity.Companion.TodoContentType.DELETE -> {
                    deleteTodoItem(position)
                }

                TodoContentActivity.Companion.TodoContentType.UPDATE -> {
                    val model = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        result.data?.getParcelableExtra(
                            TodoContentActivity.EXTRA_TODO_MODEL,
                            TodoModel::class.java
                        )
                    } else {
                        @Suppress("DEPRECATION")
                        result.data?.getParcelableExtra(
                            TodoContentActivity.EXTRA_TODO_MODEL
                        )
                    }
                    updateTodoItem(position, model)
                }
            }
        }
    }

    private val listAdapter = TodoListAdapter(
        onItemClick = { view, position ->
            Log.d("myTag", "프래그먼트 온클릭 오버라이드")
            updateTodoLauncher.launch(
                TodoContentActivity.newIntentForUpdate(requireContext(), position)
            )
        },
        onSwitchCheckedChange = { isChecked, model ->
            model.isBookmarked = isChecked
            if (isChecked) {
                sharedViewModel.setActionToBookmark(
                    ActionData(ActionData.ActionType.Add, listOf(model.copy()))
                )
            } else {
                sharedViewModel.setActionToBookmark(
                    ActionData(ActionData.ActionType.Remove, listOf(model.copy()))
                )
            }
        }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {
        binding.todoList.adapter = listAdapter
    }

    private fun initViewModel() = viewModel.also { vm ->
        vm.uiState.observe(viewLifecycleOwner) {
            listAdapter.submitList(it.list)
        }
    }

    fun addTodoItem(model: TodoModel?) {
        viewModel.addTodoItem(model)
    }

    fun deleteTodoItem(position: Int) {
        viewModel.deleteTodoItem(position)
    }

    fun updateTodoItem(position: Int, model: TodoModel?) {
        viewModel.updateTodoItem(position, model)
    }
}
