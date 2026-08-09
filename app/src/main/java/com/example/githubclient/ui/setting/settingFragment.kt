package com.example.githubclient.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.data.local.GithubCredentialDataStore
import com.example.githubclient.databinding.FragmentSettingBinding
import kotlinx.coroutines.launch

class SettingFragment: Fragment() {
    private var _binding: FragmentSettingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)

        lifecycleScope.launch {
            val context = requireContext()
            val credentials = GithubCredentialDataStore.getInstance(context).getCredentials()
            binding.inputOwner.setText(credentials.owner)
            binding.inputToken.setText(credentials.token)
        }

        binding.closeButton.setOnClickListener {
            lifecycleScope.launch {
                val context = requireContext()
                if(binding.inputOwner.text.toString().isNotEmpty()) {
                    GithubCredentialDataStore.getInstance(context).saveCredentials(
                        binding.inputOwner.text.toString(),
                        binding.inputToken.text.toString()
                    )
                }
            }
            parentFragmentManager.setFragmentResult(
                "settings_updated",
                Bundle.EMPTY
            )
            parentFragmentManager.popBackStack()
        }
    return binding.root
    }
}