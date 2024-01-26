package com.example.chall_listadapter.assignment.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chall_listadapter.R
import com.example.chall_listadapter.databinding.FragmentBookmarkListBinding

class BookmarkListFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkListFragment()
    }

    private var _binding: FragmentBookmarkListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = with(binding) {

    }

    private fun initViewModel() {

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}