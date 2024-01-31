package com.example.kakaoapi_search.imagesearch

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.kakaoapi_search.databinding.ViewGridItemBinding
import com.example.kakaoapi_search.model.ItemModel

class SearchListAdapter : ListAdapter<ItemModel, SearchListAdapter.SearchListViewHolder>(diffUtil) {
    inner class SearchListViewHolder(private val binding: ViewGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(model: ItemModel) {
            binding.also { b ->
                b.ivItemThumbnail.load(model.thumbnailURL)
                b.tvItemSite.text = model.displaySitename
                b.tvItemDatetime.text = model.datetime
                b.ivItemLove.isVisible = model.isLoved

                //ddd
                Log.d("myTag: onBind", "$model")

                b.root.setOnClickListener {
                    Log.d("myTag", "$layoutPosition: ${getItem(layoutPosition)}")
                    itemClick?.onClick(it, layoutPosition)
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

    /** 아이템 클릭에 대한 이벤트를 다른 액티비티로 넘겨주고 싶을 때, 이를 위한 인터페이스 */
    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder =
        SearchListViewHolder(
            ViewGridItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
