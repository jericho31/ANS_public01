package com.example.chall_listadapter.assignment.todo.list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.TodoListItemBinding

class TodoListAdapter : ListAdapter<TodoModel, TodoListAdapter.TodoListViewHolder>(diffUtil) {
    inner class TodoListViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: TodoModel) {
            binding.title.text = model.title
            binding.description.text = model.content
            binding.root.setOnClickListener {
                Log.d("myTag", getItem(layoutPosition).toString())
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder =
        TodoListViewHolder(TodoListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}