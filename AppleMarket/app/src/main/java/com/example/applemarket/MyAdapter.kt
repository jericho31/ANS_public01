package com.example.applemarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.applemarket.databinding.ItemRecyclerBinding
import java.text.DecimalFormat

class MyAdapter(private val itemList: MutableList<PostingData>) :
    RecyclerView.Adapter<MyAdapter.Holder>() {
    inner class Holder(binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root) {
        val iv = binding.ivItem
        val tvTitle = binding.tvItemTitle
        val tvAddress = binding.tvItemAddress
        val tvPrice = binding.tvItemPrice
        val tvComment = binding.tvItemComment
        val tvLike = binding.tvItemLike
        val ivLike = binding.ivItemLike
    }

    /** 아이템 클릭에 대한 이벤트를 다른 액티비티로 넘겨주고 싶을 때, 이를 위한 인터페이스 */
    interface ItemClick {
        fun onClick(view: View, position: Int)
        fun onLongClick(view: View?, position: Int): Boolean
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = itemList.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.apply {
            itemView.setOnClickListener { itemClick?.onClick(it, position) }
            itemView.setOnLongClickListener { itemClick?.onLongClick(it, position) ?: true }

            val item = itemList[position]
            iv.setImageResource(item.resId)
            tvTitle.text = item.title
            tvAddress.text = item.address
            tvPrice.text = "${DecimalFormat(",###").format(item.price)}원"
            tvComment.text = item.comment.toString()
            tvLike.text = item.like.toString()
            if (item.liked) ivLike.setImageResource(R.drawable.heart_red)
            else ivLike.setImageResource(R.drawable.heart_empty)
        }
    }

    override fun getItemId(position: Int): Long = position.toLong()
}