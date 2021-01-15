package se.dsek.sangbok.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import se.dsek.sangbok.databinding.SongSearchRecyclerviewItemBinding
import se.dsek.sangbok.db.Song
import java.util.*

class SongSearchListAdapter(private val clickListener: SongSearchClickListener) : ListAdapter<Song, SongSearchListAdapter.SongSearchViewHolder>(SongSearchComparator()), FastScroller.SectionIndexer {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongSearchViewHolder {
        return SongSearchViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SongSearchViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }

    override fun getSectionText(position: Int): CharSequence {
        return getItem(position).title.toUpperCase(Locale.ROOT)[0].toString()
    }

    class SongSearchViewHolder(val binding: SongSearchRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, clickListener: SongSearchClickListener) {
            binding.song = song
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): SongSearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SongSearchRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
                return SongSearchViewHolder(binding)
            }
        }
    }

    class SongSearchComparator : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

}

class SongSearchClickListener(val clickListener: (song: Song) -> Unit) {
    fun onClick(song: Song) = clickListener(song)
}
