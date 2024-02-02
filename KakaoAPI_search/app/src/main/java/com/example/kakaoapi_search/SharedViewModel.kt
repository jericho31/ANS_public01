package com.example.kakaoapi_search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kakaoapi_search.model.ItemModel

class SharedViewModel : ViewModel() {
    // 이미지 서치에서 쓸 데이터 이중으로 하지 말고 여기꺼 그대로 옵저빙하자...?
    // 근데 그러면 이미지 서치의 뷰모델 로직이 여기로 오는...?
    // 참조로 가져가서 리스트 라이브데이터 직접 건드리면 로직을 이미지 서치 쪽에서 작성하면 되는..?
    // 여기엔 리스트만 두고 이미지 서치에서 uiState에 집어넣어서 사용하면? 좋다...는 안좋다..
    // uiState 일단 버리고 여기다 리스트 라이브데이터 놓고 쓰자.

    val _uiState: MutableLiveData<List<ItemModel>> =
        MutableLiveData(emptyList())
//    val uiState: LiveData<List<ItemModel>> get() = _uiState
}
