package com.example.kakaoapi_search.imagesearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kakaoapi_search.data.ImageSearchRepository
import com.example.kakaoapi_search.model.KakaoDto
import com.example.kakaoapi_search.myContainer
import kotlinx.coroutines.launch
import retrofit2.Response

class ImageSearchViewModel(private val imageSearchRepository: ImageSearchRepository) : ViewModel() {

    private val _uiState: MutableLiveData<SearchListUiState> =
        MutableLiveData(SearchListUiState.init())
    val uiState: LiveData<SearchListUiState> get() = _uiState

    /**
     * Gets Kakao query data from the Kakao image search API Retrofit service and updates the
     * [_uiState] [] [MutableLiveData].
     */
    fun searchImage(
        query: String,
        sort: String? = null,
        page: Int? = null,
        size: Int? = null
    ) = viewModelScope.launch {
        _uiState.postValue(
            uiState.value?.copy(
                list = imageSearchRepository.searchImage(query, sort, page, size)
            )
        )
    }

    /**
     * Factory for [ImageSearchViewModel] that takes [ImageSearchRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // TODO: initializer를 감싸면 매번 새 뷰모델. 아니면 기존 뷰모델 맞나? 그러면 안감싸도?
            initializer {
                ImageSearchViewModel(imageSearchRepository = myContainer.imageSearchRepository)
            }
        }
    }
}