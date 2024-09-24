
package com.ssafy.pickit.ui.main.vote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.pickit.R
import com.ssafy.pickit.data.datasource.remote.response.VoteResponse
import com.ssafy.pickit.databinding.ItemVoteBinding
import com.ssafy.pickit.ui.main.voteDetail.VoteDetailActivity





class InProgressAdapter(private val fragment: Fragment) : ListAdapter<VoteResponse, InProgressAdapter.InProgressViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InProgressViewHolder {
        val binding = ItemVoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InProgressViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InProgressViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class InProgressViewHolder(private val binding: ItemVoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VoteResponse) {
            binding.item = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                val context = binding.root.context
                val intent = Intent(context, VoteDetailActivity::class.java).apply {

                    putExtra("voteId", item.id)
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
