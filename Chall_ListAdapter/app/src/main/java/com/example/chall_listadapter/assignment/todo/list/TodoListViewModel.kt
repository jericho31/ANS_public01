package com.example.chall_listadapter.assignment.todo.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall_listadapter.assignment.todo.TodoModel

data class ActionData(
    var type: ActionType = ActionType.Add,
    var list: List<TodoModel> = emptyList()
) {
    enum class ActionType {
        Add,
        Remove,
        Update
    }
}

class TodoListViewModel : ViewModel() {

    private val _uiState: MutableLiveData<TodoListUiState> =
        MutableLiveData(TodoListUiState.init())
    val uiState: LiveData<TodoListUiState> get() = _uiState

    fun addTodoItem(model: TodoModel?) {
        if (model == null) return

        _uiState.value = uiState.value?.copy(
            list = uiState.value?.list.orEmpty().toMutableList().apply {
                add(model)
            }
        )
    }

    fun deleteTodoItem(position: Int) {
        if (position < 0 || position >= (uiState.value?.list?.size ?: 0)) return

        _uiState.value = uiState.value?.copy(
            list = uiState.value!!.list.toMutableList().also {
                Log.d("myTag", "뷰모델 딜리트 포지션: $position")
                it.removeAt(position)
            }
        )
    }

    fun updateTodoItem(position: Int, model: TodoModel?) {
        if (position < 0 || position >= (uiState.value?.list?.size ?: 0)) return
        if (model == null) return

        _uiState.value = uiState.value!!.copy(
            list = uiState.value!!.list.toMutableList().also {
                Log.d("myTag", "뷰모델 업데이트 포지션: $position, 모델: $model")
                it[position] = model
            }
        )
    }
}
