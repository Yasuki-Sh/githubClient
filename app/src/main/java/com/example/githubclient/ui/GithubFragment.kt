package com.example.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.databinding.FragmentRepositoryBinding
import kotlinx.coroutines.launch

class GithubFragment: Fragment() {
    private val viewModel: GithubViewModel by activityViewModels()

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)

        val linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.repositoryRecyclerView.layoutManager = linearLayoutManager
        binding.repositoryRecyclerView.addItemDecoration(
            DividerItemDecoration(requireContext(), linearLayoutManager.orientation)
        )

        val adapter = GithubViewAdapter(emptyList(), onItemClick = { /*todo*/ } )// 仮で空リストを設定している
        binding.repositoryRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is GithubUiState.Loading -> {
                        adapter.updateData(emptyList())
                    }
                    is GithubUiState.Success -> {
                        adapter.updateData(uiState.repos)
                    }
                    is GithubUiState.Error -> {
                        adapter.updateData(emptyList())
                    }
                    else -> {}
                }
            }
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}