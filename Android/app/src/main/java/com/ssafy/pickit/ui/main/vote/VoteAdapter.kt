package com.ssafy.pickit.ui.main.vote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.pickit.databinding.ItemVoteBinding

class VoteAdapter(private val items: List<String>) : RecyclerView.Adapter<VoteAdapter.VoteViewHolder>() {

    inner class VoteViewHolder(private val binding: ItemVoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.voteItem = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VoteViewHolder {
        val binding = ItemVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VoteViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}