package com.example.githubclient.ui.githubRepoDetail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.FragmentRepositoryDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import io.noties.markwon.Markwon
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.html.HtmlPlugin
import io.noties.markwon.image.ImagesPlugin
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GithubRepoDetailFragment: Fragment() {
    private val viewModel: GithubRepoDetailViewModel by viewModels()

    private val markwon: Markwon by lazy {
        Markwon.builder(requireContext())
            .usePlugin(HtmlPlugin.create())
            .usePlugin(ImagesPlugin.create())
            .usePlugin(TablePlugin.create(requireContext()))
            .usePlugin(StrikethroughPlugin.create())
            .build()
    }

    private var _binding: FragmentRepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_REPO_NAME = "arg_repo_name"
        private const val ARG_OWNER = "arg_owner"
        private const val ARG_DESCRIPTION = "arg_description"


        fun newInstance(repoName: String, owner:String, description: String?): GithubRepoDetailFragment {
            val fragment = GithubRepoDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_REPO_NAME, repoName)
                putString(ARG_OWNER, owner)
                putString(ARG_DESCRIPTION, description)
            }
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoryDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoName.text = (arguments?.getString(ARG_REPO_NAME) + "の詳細")
        binding.repoDescription.text = arguments?.getString(ARG_DESCRIPTION) ?: "説明文なし"

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.readmeUiState.collect { uiState ->
                when (uiState) {
                    is GithubRepoDetailUiState.Success -> {
                        markwon.setMarkdown(binding.readme,uiState.readme)
                    }

                    is GithubRepoDetailUiState.Error -> {
                        binding.readme.text = "Error: Readme.md not found"
                    }

                    is GithubRepoDetailUiState.Loading -> {
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