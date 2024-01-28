package com.example.kakaoapi_search

import com.example.kakaoapi_search.data.KakaoData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkInterface {
    @GET("v2/search/image")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("sort") sort: String? = null,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Header("Authorization") apiKey: String = "KakaoAK ${BuildConfig.KAKAO_API_KEY}",
    ): Response<KakaoData>
}