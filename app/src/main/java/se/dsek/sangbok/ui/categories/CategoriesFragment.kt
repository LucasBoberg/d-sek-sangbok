package se.dsek.sangbok.ui.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.l4digital.fastscroll.FastScrollView
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication

class CategoriesFragment : Fragment() {

    private val categoriesViewModel: CategoriesViewModel by viewModels {
        CategoriesViewModelFactory((activity?.application as SongApplication).songRepository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_categories, container, false)
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.category_swipe)
        val fastScrollView: FastScrollView = root.findViewById(R.id.category_fastscroll_view)
        val categoryAdapter = CategoryListAdapter(CategoryClickListener { category ->
            val intent = Intent(activity, CategoryActivity::class.java).apply {
                putExtra(CATEGORY_ID, category.id)
            }
            activity?.startActivity(intent)
        })

        fastScrollView.setLayoutManager(LinearLayoutManager(context))
        fastScrollView.setAdapter(categoryAdapter)

        categoriesViewModel.allCategories.observe(viewLifecycleOwner, Observer {
                categories -> categories?.let {
            categoryAdapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        }
        })

        swipeRefreshLayout.setOnRefreshListener {
            categoriesViewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        return root
    }
}