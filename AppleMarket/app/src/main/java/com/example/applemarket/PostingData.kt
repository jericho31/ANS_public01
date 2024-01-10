package com.example.applemarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostingData(
    var id: Int,
    var resId: Int,
    var title: String,
    var desc: String,
    var user: String,
    var price: Int,
    var address: String,
    var like: Int,
    var comment: Int,
    var liked: Boolean = false
) : Parcelable
