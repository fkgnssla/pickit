package com.ssafy.pickit.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.pickit.R

class HorizontalAdapter(
    private val context: Context
) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {


    private val items = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_horizontal, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageView.setImageResource(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Method to update the list of items
    fun submitList(newItems: List<Int>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged() // Notify the adapter of data changes
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }
}
