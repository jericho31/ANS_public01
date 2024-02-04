package com.example.chall_listadapter.assignment.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chall_listadapter.assignment.todo.TodoModel
import com.example.chall_listadapter.databinding.TodoListItemBinding

class BookmarkListAdapter(
    private val onSwitchCheckedChange: (isChecked: Boolean, model: TodoModel) -> Unit
) : ListAdapter<TodoModel, BookmarkListAdapter.BookmarkListViewHolder>(object :
    DiffUtil.ItemCallback<TodoModel>() {
    override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean =
        oldItem == newItem
}) {
    inner class BookmarkListViewHolder(private val binding: TodoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: TodoModel) = binding.also { b ->
            b.title.text = model.title
            b.description.text = model.content
            b.switchBookmark.isChecked = model.isBookmarked

//            b.root.setOnClickListener {
//                Log.d("myTag", "$layoutPosition: ${getItem(layoutPosition)}")
//                itemClick?.onClick(it, layoutPosition)
//            }
            b.switchBookmark.setOnCheckedChangeListener { _, isChecked ->
                onSwitchCheckedChange(isChecked, model)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkListViewHolder =
        BookmarkListViewHolder(
            TodoListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BookmarkListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
