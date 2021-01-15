package se.dsek.sangbok.ui.search

import android.app.SearchManager
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.l4digital.fastscroll.FastScrollView
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication
import se.dsek.sangbok.ui.song.SongActivity


const val SONG_ID = "se.dsek.sangbok.SONG_ID"

class SearchActivity : AppCompatActivity() {

    lateinit var query: String

    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory((application as SongApplication).songRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                this.query = query
                searchViewModel.search(query)
            }
        }


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val fastScrollView: FastScrollView = findViewById(R.id.song_fastscroll_view)
        val songAdapter = SongSearchListAdapter(SongSearchClickListener { song ->
            val intent = Intent(this, SongActivity::class.java).apply {
                putExtra(se.dsek.sangbok.ui.song.SONG_ID, song.id)
            }
            startActivity(intent)
        })

        fastScrollView.setLayoutManager(LinearLayoutManager(this))
        fastScrollView.setAdapter(songAdapter)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)


        searchViewModel.searchResults.observe(this, Observer {
                songs -> songs?.let {
            songAdapter.submitList(it)
        }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.menu_search)
        val searchView = searchItem.actionView as SearchView
        searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)?.setBackgroundColor(Color.TRANSPARENT)
        searchView.apply {
            setIconifiedByDefault(false)
        }
        searchItem.expandActionView()
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setQuery(query, true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchViewModel.search(query)
                return false
            }

            override fun onQueryTextChange(newQuery: String): Boolean {
                searchViewModel.search(newQuery)
                return false
            }
        })
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}