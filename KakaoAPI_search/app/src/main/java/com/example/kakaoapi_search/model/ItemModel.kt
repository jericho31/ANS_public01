package com.example.kakaoapi_search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class ItemModel(
    val id: String = UUID.randomUUID().toString(),
    val thumbnailURL: String,
    val displaySitename: String,
    val datetime: String,
    var isLoved: Boolean = false
) : Parcelable
