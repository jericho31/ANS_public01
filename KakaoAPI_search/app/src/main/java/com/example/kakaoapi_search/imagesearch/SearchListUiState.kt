package com.example.kakaoapi_search.imagesearch

import com.example.kakaoapi_search.model.ItemModel

data class SearchListUiState(
    val list: List<ItemModel>
) {
    companion object {
        fun init() = SearchListUiState(
            list = emptyList()
        )
    }
}