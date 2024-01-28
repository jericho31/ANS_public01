package com.example.kakaoapi_search.imagesearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaoapi_search.NetworkClient
import com.example.kakaoapi_search.data.KakaoData
import retrofit2.Response

class ImageSearchViewModel : ViewModel() {

//    private val _uiState: MutableLiveData<TodoListUiState> =
//        MutableLiveData(TodoListUiState.init())
//    val uiState: LiveData<TodoListUiState> get() = _uiState

    private var _kakaoData: MutableLiveData<Response<KakaoData>?> =
        MutableLiveData()
    val kakaoData: LiveData<Response<KakaoData>?> get() = _kakaoData

    suspend fun communicateNetwork(
        query: String,
        sort: String? = null,
        page: Int? = null,
        size: Int? = null
    ) {
        _kakaoData.value = NetworkClient.kakaoNetwork.searchImage(query, sort, page, size)
        Log.d("kakaoData ::", kakaoData.value.toString())
    }

//    fun addTodoItem(
//        model: TodoModel?
//    ) {
//        if (model == null) {
//            return
//        }
//
//        _uiState.value = uiState.value?.copy(
//            list = uiState.value?.list.orEmpty().toMutableList().apply {
//                add(model)
//            }
//        )
//    }
}