package com.example.chall_listadapter.assignment.todo.list

import com.example.chall_listadapter.assignment.todo.TodoModel

data class TodoListUiState(
    val list: List<TodoModel>
) {
    companion object {

        fun init() = TodoListUiState(
            list = emptyList()
        )
    }
}