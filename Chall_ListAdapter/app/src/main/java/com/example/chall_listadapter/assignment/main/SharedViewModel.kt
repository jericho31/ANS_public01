package com.example.chall_listadapter.assignment.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chall_listadapter.assignment.todo.list.ActionData

class SharedViewModel : ViewModel() {
    private val _action: MutableLiveData<ActionData> = MutableLiveData(ActionData())
    val action: LiveData<ActionData> get() = _action

    fun setActionToBookmark(action: ActionData) {
        _action.value = action
    }
}
