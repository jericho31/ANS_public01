package com.example.kakaoapi_search.imagesearch

import android.util.Log
import android.view.LayoutInflater
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

                b.root.setOnClickListener {
                    Log.d("myTag:검색어댑터 아이템클릭", "$layoutPosition: ${getItem(layoutPosition)}")

                    // TODO: model이 참조로 넘어오나?
                    if (model.isLoved) {
                        removeFromSelected?.invoke(model.id)
                    } else {
                        mAddToSelected?.addToSelected(model)
                    }
                    model.isLoved = model.isLoved.not()
//                    setItemLove?.setItemLove(model.id, model.isLoved.not())
                    b.ivItemLove.isVisible = model.isLoved
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

    // addToSelected
    interface AddToSelected {
        fun addToSelected(itemModel: ItemModel)
    }

    private var mAddToSelected: AddToSelected? = null
    fun setAddToSelected(l: ((ItemModel) -> Unit)?) {
        mAddToSelected = object : AddToSelected {
            override fun addToSelected(itemModel: ItemModel) {
                l?.invoke(itemModel)
            }
        }
    }

    // removeFromSelected
    private var removeFromSelected: ((id: String) -> Unit)? = null
    fun setFunRemoveFromSelected(l: ((id: String) -> Unit)?) {
        removeFromSelected = l
    }

//    // setItemLove  - 모델이 참조로 넘어와서 그냥 모델 isLoved 값 변경해주면 되는듯?
//    private var setItemLove: ((id: String, boolean: Boolean) -> Unit)? = null
//    fun setFunSetItemLove(l: ((id: String, boolean: Boolean) -> Unit)?) {
//        setItemLove = l
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchListViewHolder =
        SearchListViewHolder(
            ViewGridItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: SearchListViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}
