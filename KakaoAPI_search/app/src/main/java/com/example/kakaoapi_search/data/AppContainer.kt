package com.example.kakaoapi_search.data

import com.example.kakaoapi_search.BuildConfig
import com.example.kakaoapi_search.network.KakaoImageApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
DefaultAppContainer의 인스턴스는 애플리케이션 클래스에 들어있어야 하는데
뷰모델 팩토리에서 저장소 인자 넣을 때 this[APPLICATION_KEY] as KakaoImageSearchApplication 이렇게 접근해서
애플리케이션의 container의 저장소를 넘겨주는데,
as KakaoImageSearchApplication 캐스팅 할 때 터진다.
컴포즈 관련인지 뭔지 모르겠음. 그냥 전역으로 만들어뒀다.
 */

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val imageSearchRepository: ImageSearchRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://dapi.kakao.com"

    private fun createOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG)
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            interceptor.level = HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder().also {
            it.connectTimeout(20, TimeUnit.SECONDS)
            it.readTimeout(20, TimeUnit.SECONDS)
            it.writeTimeout(20, TimeUnit.SECONDS)
            it.addNetworkInterceptor(interceptor)
//            // 인터셉터로도 할 수 있지만 @Headers로 대체.
//            it.addInterceptor { chain ->
//                val request = chain.request().newBuilder()
//                    .addHeader("Authorization", "KakaoAK ${BuildConfig.KAKAO_API_KEY}")
//                    .build()
//                chain.proceed(request)
//            }
        }.build()
    }

    /**
     * Use the Retrofit builder to build a retrofit object using a GsonConverterFactory
     */
    private val retrofit: Retrofit = Retrofit.Builder().also {
        it.addConverterFactory(GsonConverterFactory.create())
        it.baseUrl(baseUrl)
        it.client(createOkHttpClient())
    }.build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: KakaoImageApiService by lazy {
        retrofit.create(KakaoImageApiService::class.java)
    }

    /**
     * DI implementation for Mars photos repository
     */
    override val imageSearchRepository: ImageSearchRepository by lazy {
        NetworkImageSearchRepository(retrofitService)
    }
}
