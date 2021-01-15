package se.dsek.sangbok.ui.categories

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.l4digital.fastscroll.FastScrollView
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.ui.SongClickListener
import se.dsek.sangbok.ui.SongListAdapter
import se.dsek.sangbok.ui.song.SONG_ID
import se.dsek.sangbok.ui.song.SongActivity


const val CATEGORY_ID = "se.dsek.sangbok.CATEGORY_ID"

class CategoryActivity : AppCompatActivity() {

    private val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((application as SongApplication).songRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val id = intent.getIntExtra(CATEGORY_ID, 143)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val fastScrollView: FastScrollView = findViewById(R.id.song_fastscroll_view)
        val songAdapter = SongListAdapter(SongClickListener { song, isFavorite ->
            if (isFavorite) {
                val updateSong = song.copy(favorite = !song.favorite)
                categoryViewModel.updateSong(updateSong)
            } else {
                Log.d("DEBUG", song.favorite.toString())
                val intent = Intent(this, SongActivity::class.java).apply {
                    putExtra(SONG_ID, song.id)
                }
                startActivity(intent)
            }
        })

        fastScrollView.setLayoutManager(LinearLayoutManager(this))
        fastScrollView.setAdapter(songAdapter)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        categoryViewModel.getCategoryWithSongs(id).observe(this, Observer {
            categoryWithSongs ->
            supportActionBar?.title = categoryWithSongs.category.title
            categoryWithSongs.songs.let {
                songAdapter.submitList(it)
            }
        })
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}