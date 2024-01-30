package com.example.kakaoapi_search

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
import androidx.fragment.app.viewModels
import com.example.kakaoapi_search.databinding.FragmentImageSearchBinding
import com.example.kakaoapi_search.imagesearch.ImageSearchViewModel
import com.example.kakaoapi_search.model.Document

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

    private val viewModel: ImageSearchViewModel by viewModels { ImageSearchViewModel.Factory }
//    private val viewModel: ImageSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(this)
    }

    @SuppressLint("SetTextI18n")
    private fun initView(frag: ImageSearchFragment) = binding.also { b ->
        b.btnSearchSearch.setOnClickListener {
            Log.d("myTag", "버튼서치서치 클릭리스너")
            hideInputAndClearFocus()
            searchImage(b.etSearchInput.text.toString())
        }

        viewModel.kakaoData.observe(viewLifecycleOwner) {
            Log.d("kakaoData ::", "$it\n${it?.body()?.meta}")  //ddd
            b.tvSearchDebug.text = it.toString() + "\n\n" +
                    it?.body()?.meta + "\n\n" +
                    it?.body()?.documents?.fold("") { s: String, document: Document ->
                        s + document + "\n\n"
                    }
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

//        requireActivity().runOnUiThread {
//            binding.spinnerViewGoo.setItems(goo)
//        }

    private fun hideInputAndClearFocus() {
        requireActivity().apply {
            // TODO: 포커스 어떻게 없애냐...
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
            window.currentFocus?.clearFocus()
            currentFocus?.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            currentFocus?.clearFocus()
        }
    }

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