package com.example.chall_listadapter.assignment.todo.list

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.TodoListItemBinding

class TodoListAdapter(
    private val onItemClick: (view: View, position: Int) -> Unit,
    private val onSwitchClick: (model: TodoModel) -> Unit
) : ListAdapter<TodoModel, TodoListAdapter.TodoListViewHolder>(diffUtil) {
    inner class TodoListViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: TodoModel) = binding.also { b ->
            b.title.text = model.title
            b.description.text = model.content
            b.root.setOnClickListener {
                Log.d("myTag", "$layoutPosition: ${getItem(layoutPosition).toString()}")
                onItemClick(it, layoutPosition)
            }
            b.switchBookmark.setOnClickListener {
                onSwitchClick(model)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                if (oldItem is TodoModel && newItem is TodoModel) {
                    oldItem.id == newItem.id
                } else {
                    oldItem == newItem
                }

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder =
        TodoListViewHolder(
            TodoListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
