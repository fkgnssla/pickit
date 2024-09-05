import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.pickit.databinding.ItemChannelButtonBinding
import com.ssafy.pickit.ui.main.channel.ChannelButton

class ChannelButtonAdapter(
    private var items: List<ChannelButton>,
    private val clickListener: (Int) -> Unit
) : RecyclerView.Adapter<ChannelButtonAdapter.ChannelButtonViewHolder>() {

    inner class ChannelButtonViewHolder(private val binding: ItemChannelButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChannelButton) {
            binding.imageButton.setImageResource(item.iconResId)
            binding.imageButton.setOnClickListener { clickListener(item.id) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelButtonViewHolder {
        val binding = ItemChannelButtonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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