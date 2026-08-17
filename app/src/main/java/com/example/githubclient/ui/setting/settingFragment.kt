package com.example.githubclient.ui.setting

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class SettingFragment: Fragment() {
    private val viewModel: SettingViewModel by viewModels ()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.credentials.collect { credentials ->
                binding.inputOwner.setText(credentials.owner)
                binding.inputToken.setText(credentials.token)
            }
        }

        binding.closeButton.setOnClickListener {
            val owner = binding.inputOwner.text.toString()
            val token = binding.inputToken.text.toString()
            Log.d("SettingFragment", "owner=[$owner], token=[$token]")
            lifecycleScope.launch {
                viewModel.saveCredentials(owner, token)
                parentFragmentManager.setFragmentResult(
                    "settings_updated",
                    Bundle.EMPTY
                )
                parentFragmentManager.popBackStack()
            }
        }
    return binding.root
    }
}