package com.example.githubclient.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubclient.data.model.GithubResponse
import com.example.githubclient.databinding.ItemRepositoryBinding

class GithubViewAdapter(
    private var repositoryData: List<GithubResponse>,
    private val onItemClick: (GithubResponse) -> Unit
) : RecyclerView.Adapter<GithubViewAdapter.GithubViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<GithubResponse>) {
        repositoryData = newData
        notifyDataSetChanged()
    }

    inner class GithubViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: GithubResponse) {
            binding.repositoryName.text = repo.fullName
            binding.repositoryUrl.text = repo.htmlUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GithubViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GithubViewHolder, position: Int) {
        holder.bind(repositoryData[position])
        holder.itemView.setOnClickListener {
            onItemClick(repositoryData[position])
        }
    }

    override fun getItemCount(): Int = repositoryData.size
}