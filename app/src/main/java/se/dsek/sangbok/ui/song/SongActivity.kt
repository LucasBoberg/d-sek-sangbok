package se.dsek.sangbok.ui.song

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.thefuntasty.hauler.HaulerView
import com.thefuntasty.hauler.setOnDragDismissedListener
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication
import se.dsek.sangbok.db.Song


const val SONG_ID = "se.dsek.sangbok.SONG_ID"

class SongActivity : AppCompatActivity() {

    private lateinit var menu: Menu

    private lateinit var song: Song

    private val songViewModel: SongViewModel by viewModels {
        SongViewModelFactory((application as SongApplication).songRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        val id = intent.getIntExtra(SONG_ID, 1001)

        val haulerView: HaulerView = findViewById(R.id.hauler)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val titleView: TextView = findViewById(R.id.title)
        val melodyView: TextView = findViewById(R.id.melody)
        val lyricsView: TextView = findViewById(R.id.lyrics)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        songViewModel.getSong(id).observe(this, Observer {
            song = it
            titleView.text = it.title
            melodyView.text = it.melody
            lyricsView.text = it.lyrics
            songViewModel.updateSong(
                it.copy(
                    lastViewed = LocalDateTime.now().toEpochSecond(
                        ZoneOffset.UTC
                    )
                )
            )
            if (this::menu.isInitialized) {
                if (it.favorite) {
                    menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_colored_24)
                } else {
                    menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24)
                }
            }

        })

        haulerView.setOnDragDismissedListener {
            finish() // finish activity when dismissed
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.song_menu, menu)

        if (this::song.isInitialized && song.favorite) {
            menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_colored_24)
        } else {
            menu.getItem(0).icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24)
        }
        this.menu = menu

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.menu_favorite -> {
                songViewModel.updateSong(song.copy(favorite = !song.favorite))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}