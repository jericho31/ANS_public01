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


//    class DateTypeAdapter : JsonDeserializer<Date>, JsonSerializer<Date> {
//        private val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.US)
//        private val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA)
//
//        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
//            return inputFormat.parse(json.asString)!!
//        }
//
//        override fun serialize(src: Date, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
//            return JsonPrimitive(outputFormat.format(src))
//        }
//    }
//
//    private val gson = GsonBuilder()
//        .registerTypeAdapter(Date::class.java, DateTypeAdapter())
//        .create()

//    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    /*
    위에 애들은 gson이 특정 형식으로 오는 날짜 json 데이터를 Date 타입으로 넣을 수 있도록 하기 위한 건가?
    그러면 내 입맛대로 형식 바꿔 출력하려면 결국 repository 같은 곳에서 포맷 만들어서 바꿔줘야 하는건가?
    그런듯.
     */

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
