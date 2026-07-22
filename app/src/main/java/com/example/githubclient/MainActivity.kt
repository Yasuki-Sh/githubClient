package com.example.githubclient

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.githubclient.databinding.ActivityMainBinding
import com.example.githubclient.ui.GithubFragment
import com.example.githubclient.ui.GithubViewModel

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

        viewModel.fetchGithubPrivateRepos()

        supportFragmentManager.beginTransaction()
            .replace(R.id.repositoryList, GithubFragment())
            .commit()
    }
}
