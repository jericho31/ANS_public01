package com.example.chall_listadapter.assignment.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.assignment.todo.list.ActionData
import com.example.chall_listadapter.assignment.todo.list.TodoListUiState

class BookmarkListViewModel : ViewModel() {

    private val _uiState: MutableLiveData<TodoListUiState> =
        MutableLiveData(TodoListUiState.init())
    val uiState: LiveData<TodoListUiState> get() = _uiState

    fun executeAction(action: ActionData) {
        when (action.type) {
            ActionData.ActionType.Add -> addToList(action.list)
            ActionData.ActionType.Remove -> TODO()
            ActionData.ActionType.Update -> TODO()
        }
    }

//    fun addToList(model: TodoModel) =
//        _uiState.run { value = value!!.copy(list = ArrayList(value!!.list).apply { add(model) }) }

    fun addToList(list: List<TodoModel>) =
        // TODO: 그냥 리스트에 추가해도 옵저빙이 되나?
        _uiState.run { value = value!!.copy(list = ArrayList(value!!.list).apply { addAll(list) }) }
}
