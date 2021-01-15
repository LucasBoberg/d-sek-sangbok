package se.dsek.sangbok.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import se.dsek.sangbok.R
import se.dsek.sangbok.databinding.SongRecyclerviewItemBinding
import se.dsek.sangbok.db.Song
import java.util.*

class SongListAdapter(private val clickListener: SongClickListener) : ListAdapter<Song, SongListAdapter.SongViewHolder>(SongComparator()), FastScroller.SectionIndexer {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        context = parent.context
        return SongViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener, context)
    }

    override fun getSectionText(position: Int): CharSequence {
        return getItem(position).title.toUpperCase(Locale.ROOT)[0].toString()
    }

    class SongViewHolder(val binding: SongRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song, clickListener: SongClickListener, context: Context) {
            binding.song = song
            if (song.favorite) {
                binding.favoriteIcon.setColorFilter(ContextCompat.getColor(context, R.color.primary))
            } else {
                binding.favoriteIcon.setColorFilter(ContextCompat.getColor(context, R.color.not_favorite))
            }
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): SongViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SongRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
                return SongViewHolder(binding)
            }
        }
    }

    class SongComparator : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }

}

class SongClickListener(val clickListener: (song: Song, isFavorite: Boolean) -> Unit) {
    fun onClick(song: Song) = clickListener(song, false)
    fun onFavoriteClick(song: Song) = clickListener(song, true)
}
