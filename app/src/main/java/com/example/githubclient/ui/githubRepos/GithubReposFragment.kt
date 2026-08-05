package com.example.githubclient.ui.githubRepos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubclient.R
import com.example.githubclient.databinding.FragmentRepositoryBinding
import com.example.githubclient.ui.githubRepoDetail.GithubRepoDetailFragment
import com.example.githubclient.ui.setting.SettingFragment
import kotlinx.coroutines.launch

class GithubReposFragment: Fragment() {
    private val viewModel: GithubReposViewModel by viewModels {
        GithubReposViewModelFactory(
            requireContext()
        )
    }

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

        val adapter = GithubViewAdapter(emptyList()) { repo ->
            val detailFragment =
                GithubRepoDetailFragment.newInstance(repo.name, repo.owner.login, repo.description)
            parentFragmentManager.beginTransaction()
                .replace(R.id.repositoryList, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.repositoryRecyclerView.adapter = adapter

        binding.buttonRefresh.setOnClickListener {
            viewModel.getRepos()
        }

        binding.buttonSettings.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.repositoryList, SettingFragment())
                .addToBackStack(null)
                .commit()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is GithubUiState.Loading -> {
                        binding.fetchState.text = "Loading..."
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is GithubUiState.Success -> {
                        adapter.updateData(uiState.repos)
                        binding.fetchState.text = "リポジトリ数：${uiState.repos.size}"
                        binding.progressBar.visibility = View.GONE
                    }
                    is GithubUiState.Error -> {
                        binding.fetchState.text = "Error"
                        binding.progressBar.visibility = View.GONE
                    }
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