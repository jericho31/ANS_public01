package com.example.kakaoapi_search.model

import com.google.gson.annotations.SerializedName

data class KakaoData(
    val documents: List<Document>,
    val meta: Meta
)

data class Document(
    val collection: Collection,
    val datetime: String,

    @SerializedName("display_sitename")
    val displaySitename: String,

    @SerializedName("doc_url")
    val docURL: String,

    val height: Long,

    @SerializedName("image_url")
    val imageURL: String,

    @SerializedName("thumbnail_url")
    val thumbnailURL: String,

    val width: Long
)

enum class Collection(val value: String) {
    @SerializedName("blog")
    Blog("blog"),

    @SerializedName("cafe")
    Cafe("cafe"),

    @SerializedName("etc")
    Etc("etc"),

    @SerializedName("news")
    News("news");
}

data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean,

    @SerializedName("pageable_count")
    val pageableCount: Long,

    @SerializedName("total_count")
    val totalCount: Long
)
