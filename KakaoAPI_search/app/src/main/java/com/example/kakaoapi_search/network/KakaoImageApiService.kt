package com.example.kakaoapi_search.network

import com.example.kakaoapi_search.BuildConfig
import com.example.kakaoapi_search.model.KakaoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 recency(최신순), 기본 값 accuracy
 * page	Integer	결과 페이지 번호, 1~50 사이의 값, 기본 값 1
 * size	Integer	한 페이지에 보여질 문서 수, 1~80 사이의 값, 기본 값 80
 */
interface KakaoImageApiService {
    @Headers("Authorization: KakaoAK ${BuildConfig.KAKAO_API_KEY}")
    @GET("/v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null
    ): Response<KakaoDto>
}
