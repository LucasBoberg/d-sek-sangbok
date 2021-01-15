package se.dsek.sangbok.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.l4digital.fastscroll.FastScroller
import se.dsek.sangbok.databinding.CategoryRecyclerviewItemBinding
import se.dsek.sangbok.db.Category
import java.util.*

class CategoryListAdapter(private val clickListener: CategoryClickListener) : ListAdapter<Category, CategoryListAdapter.CategoryViewHolder>(CategoryComparator()), FastScroller.SectionIndexer {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, clickListener)
    }

    override fun getSectionText(position: Int): CharSequence {
        return getItem(position).title.toUpperCase(Locale.ROOT)[0].toString()
    }

    class CategoryViewHolder(val binding: CategoryRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category, clickListener: CategoryClickListener) {
            binding.category = category
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun create(parent: ViewGroup): CategoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }
    }

    class CategoryComparator : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }

}

class CategoryClickListener(val clickListener: (category: Category) -> Unit) {
    fun onClick(category: Category) = clickListener(category)
}
