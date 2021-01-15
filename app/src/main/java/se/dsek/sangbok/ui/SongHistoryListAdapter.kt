package se.dsek.sangbok.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import org.threeten.bp.Instant
import se.dsek.sangbok.databinding.SongHistoryRecyclerviewItemBinding
import se.dsek.sangbok.db.Song

class SongHistoryListAdapter(private val clickListener: SongHistoryClickListener) : ListAdapter<Song, SongHistoryListAdapter.SongHistoryViewHolder>(SongHistoryComparator()), FastScroller.SectionIndexer {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHistoryViewHolder {
        return SongHistoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SongHistoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }

    override fun getSectionText(position: Int): CharSequence {
        return Instant.ofEpochSecond(getItem(position).lastViewed).toString()
    }

    class SongHistoryViewHolder(val binding: SongHistoryRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, clickListener: SongHistoryClickListener) {
            binding.song = song
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): SongHistoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SongHistoryRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
                return SongHistoryViewHolder(binding)
            }
        }
    }

    class SongHistoryComparator : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

}

class SongHistoryClickListener(val clickListener: (song: Song, remove: Boolean) -> Unit) {
    fun onClick(song: Song) = clickListener(song, false)
    fun onRemoveClick(song: Song) = clickListener(song, true)
}
