package com.example.kakaoapi_search.mybox

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kakaoapi_search.SharedViewModel
import com.example.kakaoapi_search.databinding.FragmentMyboxBinding
import com.example.kakaoapi_search.model.ItemModel

//// Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyboxFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyboxFragment(private val selectedItems: MutableList<ItemModel>) : Fragment() {
//    // Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null

    private var _binding: FragmentMyboxBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter = MyboxAdapter()

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
        _binding = FragmentMyboxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
//        initViewModel(binding)
    }

    private fun initView() = binding.also { b ->
        b.rvMybox.adapter = adapter
        b.rvMybox.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        // 뷰홀더 아이템 클릭 시 액티비티의 '선택된 이미지 리스트'에서 제거하기 위한 람다식
        adapter.setFunRemoveItemFromSelected { id ->
            val pos = selectedItems.indexOfFirst { it.id == id }
            if (pos < 0) {
                Log.e("myTag:removeItemFromSelected", "error: id 같은 게 없음")
                return@setFunRemoveItemFromSelected
            }

            val list = sharedViewModel._uiState.value!!
            val pos2 = list.indexOfFirst { it.id == id }
            if (pos2 >= 0) {
                sharedViewModel._uiState.value = ArrayList(list).also { newList ->
                    newList[pos2] = selectedItems[pos].copy(isLoved = false)
                }
            }

            selectedItems.removeAt(pos)
            update()
        }
    }

    override fun onResume() {
        update()
        super.onResume()
    }

    fun update() {
        adapter.submitList(ArrayList(selectedItems)) {
            binding.tvMyboxNothing.isVisible = selectedItems.size == 0
        }
    }

//    @SuppressLint("SetTextI18n")
//    private fun initViewModel(b: FragmentMyboxBinding) = viewModel.also { vm ->
//        vm.kakaoDto.observe(viewLifecycleOwner) {
//            Log.d("kakaoData ::", "$it\n${it?.body()?.meta}")  //ddd
////            b.tvSearchDebug.text = it.toString() + "\n\n" +
////                    it?.body()?.meta + "\n\n" +
////                    it?.body()?.documents?.fold("") { s: String, document: Document ->
////                        s + document + "\n\n"
////                    }  //ddd
//        }
//
//        vm.uiState.observe(viewLifecycleOwner) {
//            adapter.submitList(it.list)
//            b.tvSearchDebug.text = it.list.fold("") { acc, model ->
//                "$acc\n\n$model"
//            }  //ddd
//        }
//    }

    companion object {
        /*
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyboxFragment.
         */
        // Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(selectedItems: MutableList<ItemModel>) =
            MyboxFragment(selectedItems).apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
            }
    }
}