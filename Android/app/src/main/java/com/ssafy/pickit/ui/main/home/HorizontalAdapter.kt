package com.ssafy.pickit.ui.main.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ssafy.pickit.R
import com.ssafy.pickit.domain.entity.VotePopularData
import com.ssafy.pickit.ui.main.result.ResultActivity
import com.ssafy.pickit.ui.main.voteDetail.VoteDetailActivity

class HorizontalAdapter(
    private val context: Context,

) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {


    private val items = mutableListOf<VotePopularData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val voteData = items[position]


        Glide.with(context)
            .load(voteData.thumbnail)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            if (voteData.isVote) {

                val intent = Intent(context, ResultActivity::class.java).apply {
                    putExtra("voteSessionId", voteData.id)
                }
                context.startActivity(intent)
            } else {

                val intent = Intent(context, VoteDetailActivity::class.java).apply {
                    putExtra("voteSessionId", voteData.id)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun submitList(newItems: List<VotePopularData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }
}
