package com.example.applemarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostingData(
    val id: Int,
    val resId: Int,
    val title: String,
    val desc: String,
    val user: String,
    val price: Int,
    val address: String,
    val like: Int,
    val comment: Int
) : Parcelable
