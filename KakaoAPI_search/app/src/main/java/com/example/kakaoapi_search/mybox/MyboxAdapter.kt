package com.example.kakaoapi_search.mybox

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kakaoapi_search.databinding.ViewGridItemBinding
import com.example.kakaoapi_search.model.ItemModel

class MyboxAdapter : ListAdapter<ItemModel, MyboxAdapter.MyboxViewHolder>(diffUtil) {
    inner class MyboxViewHolder(private val binding: ViewGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ItemModel) {
            binding.also { b ->
                b.ivItemThumbnail.load(model.thumbnailURL)
                b.tvItemSite.text = model.displaySitename
                b.tvItemDatetime.text = model.datetime

                b.root.setOnClickListener {
                    Log.d("myTag:마이박스어댑터 아이템클릭", "$layoutPosition: ${getItem(layoutPosition)}")

                }
            }
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<ItemModel>() {
            override fun areItemsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ItemModel, newItem: ItemModel): Boolean =
                oldItem == newItem
        }
    }

    // TODO: 좋아요 삭제 인터페이스

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyboxViewHolder =
        MyboxViewHolder(
            ViewGridItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MyboxViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
