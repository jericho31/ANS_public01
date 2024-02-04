package com.example.chall_listadapter.assignment.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.chall_listadapter.assignment.main.SharedViewModel
import com.example.chall_listadapter.databinding.FragmentBookmarkListBinding

class BookmarkListFragment : Fragment() {

    companion object {
        fun newInstance() = BookmarkListFragment()
    }

    private var _binding: FragmentBookmarkListBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: BookmarkListViewModel by viewModels()

    private val adapter = BookmarkListAdapter(
        onSwitchCheckedChange = { isChecked, model -> }  // TODO: impl
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun initView() = binding.also { b ->
        b.rvBookmark.adapter = adapter
    }

    private fun initViewModel() = viewModel.also { vm ->
        vm.uiState.observe(viewLifecycleOwner) { adapter.submitList(it.list) }
        sharedViewModel.action.observe(viewLifecycleOwner) { vm.executeAction(it) }
    }
}