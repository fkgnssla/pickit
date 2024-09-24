package com.ssafy.pickit.ui.main.vote

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.databinding.ItemVoteBinding
import com.ssafy.pickit.ui.main.result.ResultActivity

class CompletedAdapter : ListAdapter<VoteResponse, CompletedAdapter.CompletedViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompletedViewHolder {
        val binding = ItemVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompletedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CompletedViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class CompletedViewHolder(private val binding: ItemVoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VoteResponse) {
            binding.item = item


            binding.overlayView.visibility = View.VISIBLE
            binding.endTextView.visibility = View.VISIBLE

            binding.executePendingBindings()
            binding.root.setOnClickListener {
                val context = binding.root.context
                val voteSessionId = item.id

                val intent = Intent(context, ResultActivity::class.java).apply {
                    putExtra("voteSessionId", voteSessionId)
                }
                context.startActivity(intent)
            }
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<VoteResponse>() {
        override fun areItemsTheSame(oldItem: VoteResponse, newItem: VoteResponse): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: VoteResponse, newItem: VoteResponse): Boolean =
            oldItem == newItem
    }
}
