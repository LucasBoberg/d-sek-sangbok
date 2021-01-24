package se.dsek.sangbok.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.l4digital.fastscroll.FastScrollView
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication
import se.dsek.sangbok.ui.SongClickListener
import se.dsek.sangbok.ui.SongListAdapter
import se.dsek.sangbok.ui.song.SONG_ID
import se.dsek.sangbok.ui.song.SongActivity

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity?.application as SongApplication).songRepository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.song_swipe)
        val fastScrollView: FastScrollView = root.findViewById(R.id.song_fastscroll_view)
        val songAdapter = SongListAdapter(SongClickListener { song, isFavorite ->
            if (isFavorite) {
                val updateSong = song.copy(favorite = !song.favorite)
                homeViewModel.updateSong(updateSong)
            } else {
                Log.d("DEBUG", song.favorite.toString())
                val intent = Intent(activity, SongActivity::class.java).apply {
                    putExtra(SONG_ID, song.id)
                }
                activity?.startActivity(intent)
            }
        })

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)

        val firstRun: Boolean = sharedPref.getBoolean(getString(R.string.first_run), true)

        if (firstRun) {
            sharedPref.edit().putBoolean(getString(R.string.first_run), false).apply()
            homeViewModel.refresh()
        }

        fastScrollView.setLayoutManager(LinearLayoutManager(context))
        fastScrollView.setAdapter(songAdapter)

        homeViewModel.allSongs.observe(viewLifecycleOwner, Observer {
            songs -> songs?.let {
                songAdapter.submitList(it)
                swipeRefreshLayout.isRefreshing = false
            }
        })

        swipeRefreshLayout.setOnRefreshListener {
            homeViewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        return root
    }
}