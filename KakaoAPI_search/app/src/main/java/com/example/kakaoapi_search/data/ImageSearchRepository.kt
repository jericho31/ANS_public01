package com.example.kakaoapi_search.data

import com.example.kakaoapi_search.model.ItemModel
import com.example.kakaoapi_search.model.KakaoDto
import com.example.kakaoapi_search.network.KakaoImageApiService
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

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
    // 쿼리 받은 데이터를 내 모델에 맞게 가공해서 반환
    override suspend fun searchImage(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): List<ItemModel> = convertResponseToItems(searchImageGetResponse(query, sort, page, size))

    /** Fetches search query data from kakaoImageApi*/
    private suspend fun searchImageGetResponse(
        query: String,
        sort: String?,
        page: Int?,
        size: Int?
    ): Response<KakaoDto> = kakaoImageApiService.searchImage(query, sort, page, size)

//    private fun aa(date: String): String {
//        val oldFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
//        val newFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//
//        val date = oldFormat.parse(date)
//        return newFormat.format(date ?: "0000-00-00 00:00:00")
//    }

    private val formatter =
        SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    private fun convertResponseToItems(response: Response<KakaoDto>): List<ItemModel> {
        if (response.body() == null) return emptyList()

        return response.body()!!.documents.map { doc ->
            ItemModel(
                thumbnailURL = doc.thumbnailURL,
                displaySitename = doc.displaySitename,
                datetime = formatter.format(doc.datetime)
            )
        }
    }
}
