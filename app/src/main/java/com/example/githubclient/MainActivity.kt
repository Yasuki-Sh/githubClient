package com.example.githubclient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.ui.GithubFragment
import com.example.githubclient.ui.GithubUiState
import com.example.githubclient.ui.GithubViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val viewModel: GithubViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel.fetchGithubRepos()

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                when (uiState) {
                    is GithubUiState.Loading -> {
                        binding.textView.text = "Loading..."
                    }
                    is GithubUiState.Success -> {
                        binding.textView.text = "${uiState.repos.size}個のリポジトリを表示しています"
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.repositoryList, GithubFragment())
                            .commit()
                    }
                    is GithubUiState.Error -> {
                        binding.textView.text = "Repository Data Fetch Error"
                    }
                    else -> {}
                }
            }
        }
    }
}
