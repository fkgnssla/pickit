
package com.ssafy.pickit.ui.main.vote

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.pickit.R
import com.ssafy.pickit.data.datasource.remote.response.vote.VoteListDataResponse
import com.ssafy.pickit.databinding.ItemVoteBinding
import com.ssafy.pickit.domain.entity.VoteListData
import com.ssafy.pickit.ui.main.result.ResultActivity
import com.ssafy.pickit.ui.main.voteDetail.VoteDetailActivity





class BroadCastInProgressAdapter(private val activity: AppCompatActivity) : ListAdapter<VoteListData, BroadCastInProgressAdapter.InProgressViewHolder>(DiffCallback()) {

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

        fun bind(item: VoteListData) {
            binding.item = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                val context = binding.root.context
                if (item.isVote) {

                    val intent = Intent(context, ResultActivity::class.java).apply {
                        putExtra("voteSessionId", item.id)
                    }
                    context.startActivity(intent)
                } else {

                    val intent = Intent(context, VoteDetailActivity::class.java).apply {
                        putExtra("voteSessionId", item.id)
                    }
                    context.startActivity(intent)
                }
            }
        }


    }



    private class DiffCallback : DiffUtil.ItemCallback<VoteListData>() {
        override fun areItemsTheSame(oldItem: VoteListData, newItem: VoteListData): Boolean =
            oldItem.title == newItem.title

        override fun areContentsTheSame(oldItem: VoteListData, newItem: VoteListData): Boolean =
            oldItem == newItem
    }
}
