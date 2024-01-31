package com.example.kakaoapi_search.data

import com.example.kakaoapi_search.model.ItemModel
import com.example.kakaoapi_search.model.KakaoData
import com.example.kakaoapi_search.network.KakaoImageApiService
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
    ): List<ItemModel>
}

/**
 * Network Implementation of Repository that fetch search query data from kakaoImageApi.
 */
class NetworkImageSearchRepository(
    private val kakaoImageApiService: KakaoImageApiService
) : ImageSearchRepository {
    /** Fetches search query data from kakaoImageApi*/
    private suspend fun searchImageGetResponse(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Response<KakaoData> = kakaoImageApiService.searchImage(query, sort, page, size)

    override suspend fun searchImage(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): List<ItemModel> = convertResponseToItems(searchImageGetResponse(query, sort, page, size))

    private fun convertResponseToItems(response: Response<KakaoData>): List<ItemModel> {
        if (response.body() == null) return emptyList()

        return response.body()!!.documents.map { doc ->
            ItemModel(
                thumbnailURL = doc.thumbnailURL,
                displaySitename = doc.displaySitename,
                datetime = doc.datetime
            )
        }
    }
}
