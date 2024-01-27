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

class TodoListAdapter : ListAdapter<TodoModel, TodoListAdapter.TodoListViewHolder>(diffUtil) {
    inner class TodoListViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: TodoModel) {
            binding.title.text = model.title
            binding.description.text = model.content
            binding.root.setOnClickListener {
                Log.d("myTag", "$layoutPosition: ${getItem(layoutPosition).toString()}")
                itemClick?.onClick(it, layoutPosition)
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
                oldItem == newItem
        }
    }

    /** 아이템 클릭에 대한 이벤트를 다른 액티비티로 넘겨주고 싶을 때, 이를 위한 인터페이스 */
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder =
        TodoListViewHolder(
            TodoListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}