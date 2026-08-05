package com.example.githubclient.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.githubclient.data.local.DataStore
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
            val credentials = DataStore(context).getCredentials()
            binding.inputOwner.setText(credentials.owner)
            binding.inputToken.setText(credentials.token)
        }


        binding.saveButton.setOnClickListener {
            lifecycleScope.launch {
                val context = requireContext()
                if(binding.inputOwner.text.toString().isNotEmpty()) {
                    DataStore(context).saveCredentials(
                        binding.inputOwner.text.toString(),
                        binding.inputToken.text.toString()
                    )
                    Toast.makeText(context, "Saved Credentials: ${binding.inputOwner.text}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.closeButton.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    return binding.root
    }
}