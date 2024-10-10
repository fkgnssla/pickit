import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ssafy.pickit.databinding.ItemChannelButtonBinding
import com.ssafy.pickit.ui.main.channel.ChannelButton

class ChannelButtonAdapter(
    private var items: List<ChannelButton>,
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<ChannelButtonAdapter.ChannelButtonViewHolder>() {

    inner class ChannelButtonViewHolder(private val binding: ItemChannelButtonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChannelButton) {
            Glide.with(binding.imageButton.context)
                .load(item.iconResId)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions().centerCrop())
                .into(binding.imageButton)

            binding.imageButton.setOnClickListener { clickListener(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelButtonViewHolder {
        val binding =
            ItemChannelButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChannelButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChannelButtonViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun updateItems(newItems: List<ChannelButton>) {
        items = newItems
        notifyDataSetChanged()
    }
}