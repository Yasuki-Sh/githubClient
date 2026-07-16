package com.example.githubclient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.ui.GithubUiState
import com.example.githubclient.ui.GithubViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel = GithubViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.textView.text = "This text is test for the binding"
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.fetchGithubRepos()
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is GithubUiState.Loading -> {
                        binding.textView.text = "Loading..."
                    }
                    is GithubUiState.Success -> {
                        val repos = state.repos.joinToString("\n") { repo ->
                            "${repo.fullName} - ${repo.htmlUrl}"
                        }
                        binding.textView.text = repos
                    }
                    is GithubUiState.Error -> {
                        binding.textView.text = "Error fetching repos"
                    }
                }
            }
        }
    }
}
