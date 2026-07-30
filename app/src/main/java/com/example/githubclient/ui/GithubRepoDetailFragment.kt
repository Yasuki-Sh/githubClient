package com.example.githubclient.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.RepositoryDetailsBinding
import kotlinx.coroutines.launch

class GithubRepoDetailFragment: Fragment() {
    private val viewModel: GithubViewModel by activityViewModels()

    private var _binding: RepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_NAME = "arg_name"
        private const val ARG_OWNER = "arg_owner"
        private const val ARG_DESCRIPTION = "arg_description"


        fun newInstance(name: String, login:String, description: String?, readme: String?): GithubRepoDetailFragment {
            val fragment = GithubRepoDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_NAME, name)
                putString(ARG_OWNER, login)
                putString(ARG_DESCRIPTION, description)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoName.text = (arguments?.getString(ARG_NAME) + "の詳細")
        binding.repoDescription.text = arguments?.getString(ARG_DESCRIPTION) ?: "説明文なし"

        if (savedInstanceState == null) {
            viewModel.fetchReadme(
                arguments?.getString(ARG_OWNER) ?: "",
                arguments?.getString(ARG_NAME) ?: ""
            )
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.readmeUiState.collect { uiState ->
                when (uiState) {
                    is GithubReadmeUiState.Success -> {
                        binding.readme.text = uiState.readme
                    }

                    is GithubReadmeUiState.Error -> {
                        binding.readme.text = "Error: Readme.md not found"
                    }

                    is GithubReadmeUiState.Loading -> {
                        binding.readme.text = "Loading..."
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}