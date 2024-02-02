package com.example.kakaoapi_search.imagesearch

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kakaoapi_search.SharedViewModel
import com.example.kakaoapi_search.databinding.FragmentImageSearchBinding
import com.example.kakaoapi_search.model.ItemModel

//// Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ImageSearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ImageSearchFragment : Fragment() {
//    // Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: ImageSearchViewModel
            by viewModels { ImageSearchViewModel.createFactory(sharedViewModel) }

    private val adapter = SearchListAdapter()

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        arguments?.let {
////            param1 = it.getString(ARG_PARAM1)
////            param2 = it.getString(ARG_PARAM2)
////        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(this)
        initViewModel(binding)
    }

    private fun initView(frag: ImageSearchFragment) = binding.also { b ->
        b.rvSearch.adapter = adapter
        b.rvSearch.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        b.btnSearchSearch.setOnClickListener {
            Log.d("myTag", "버튼서치서치 클릭리스너")  //ddd
            hideInputAndClearFocus()
            searchImage(b.etSearchInput.text.toString())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel(b: FragmentImageSearchBinding) = viewModel.also { vm ->
        vm.uiState.observe(viewLifecycleOwner) {
            Log.d("myTag:검색프래그먼트 uiState 옵저버", it.toString())  //ddd
            adapter.submitList(it)  // 리스트 객체 새로 만들어 넣지 않아도 괜찮은듯? 게터 거치면서 새로 생성되나??
//            adapter.submitList(ArrayList(it))

//            b.tvSearchDebug.text = it.list.fold("") { acc, model ->
//                "$acc\n\n$model"
//            }  //ddd
        }
    }

    override fun onResume() {
        super.onResume()

        hideInputAndClearFocus()
    }

    private fun searchImage(
        query: String,
        sort: String? = null,
        page: Int? = null,
        size: Int? = null
    ) = viewModel.searchImage(query, sort, page, size)

    private fun hideInputAndClearFocus() {
        requireActivity().apply {
            // TODO: 포커스 어떻게 없애냐... 근데 없애지 않아도 좋을 듯...?
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            window.currentFocus?.clearFocus()
            currentFocus?.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }
    }

    // 뷰홀더 아이템클릭 -> 메인액티비티 리스트 접근을 위한 람다식 넘겨주기
    fun setAdapterAddToSelected(l: ((ItemModel) -> Unit)?) =
        adapter.setAddToSelected(l)

    fun setAdapterRemoveFromSelected(l: ((id: String) -> Unit)?) =
        adapter.setFunRemoveFromSelected(l)

    companion object {
        /*
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ImageSearchFragment.
         */
        // Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            ImageSearchFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}