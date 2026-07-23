package com.example.githubclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.githubclient.databinding.RepositoryDetailsBinding

class GithubRepoDetailFragment: Fragment() {
    private val viewModel: GithubViewModel by activityViewModels()

    private var _binding: RepositoryDetailsBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val ARG_FULL_NAME = "arg_full_name"
        private const val ARG_DESCRIPTION = "arg_description"

        fun newInstance(fullName: String, description: String?): GithubRepoDetailFragment {
            val fragment = GithubRepoDetailFragment()
            fragment.arguments = Bundle().apply {
                putString(ARG_FULL_NAME, fullName+"の詳細")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.repoName.text = arguments?.getString(ARG_FULL_NAME)
        binding.repoDescription.text = arguments?.getString(ARG_DESCRIPTION) ?: "説明文なし"
        binding.readme.text = "Binding readme.md"
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}