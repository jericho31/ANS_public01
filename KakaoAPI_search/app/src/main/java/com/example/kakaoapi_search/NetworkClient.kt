package com.example.kakaoapi_search

// 삭제 예정

//import com.example.kakaoapi_search.network.KakaoImageApiService
//import okhttp3.OkHttpClient
//import okhttp3.logging.HttpLoggingInterceptor
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//object NetworkClient {
//    private const val KAKAO_BASE_URL = "https://dapi.kakao.com"
//
//    private fun createOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor()
//
//        if (BuildConfig.DEBUG)
//            interceptor.level = HttpLoggingInterceptor.Level.BODY
//        else
//            interceptor.level = HttpLoggingInterceptor.Level.NONE
//
//        return OkHttpClient.Builder().also {
//            it.connectTimeout(20, TimeUnit.SECONDS)
//            it.readTimeout(20, TimeUnit.SECONDS)
//            it.writeTimeout(20, TimeUnit.SECONDS)
//            it.addNetworkInterceptor(interceptor)
//        }.build()
//    }
//
//    private val kakaoRetrofit = Retrofit.Builder().also {
//        it.baseUrl(KAKAO_BASE_URL)
//        it.addConverterFactory(GsonConverterFactory.create())
//        it.client(createOkHttpClient())
//    }.build()
//
//    val kakaoNetwork: KakaoImageApiService = kakaoRetrofit.create(KakaoImageApiService::class.java)
//}