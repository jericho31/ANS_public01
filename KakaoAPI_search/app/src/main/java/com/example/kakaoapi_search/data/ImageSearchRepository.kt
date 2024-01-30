package com.example.kakaoapi_search.data

import com.example.kakaoapi_search.network.KakaoImageApiService
import com.example.kakaoapi_search.model.KakaoData
import retrofit2.Response

/**
 * Repository that fetch search query data from kakaoImageApi.
 */
interface ImageSearchRepository {
    /** Fetches search query data from kakaoImageApi */
    suspend fun searchImage(
        query: String,
        sort: String? = null,
        page: Int? = null,
        size: Int? = null
    ): Response<KakaoData>
}

/**
 * Network Implementation of Repository that fetch search query data from kakaoImageApi.
 */
class NetworkImageSearchRepository(
    private val kakaoImageApiService: KakaoImageApiService
) : ImageSearchRepository {
    /** Fetches search query data from kakaoImageApi*/
    override suspend fun searchImage(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Response<KakaoData> = kakaoImageApiService.searchImage(query, sort, page, size)
}
