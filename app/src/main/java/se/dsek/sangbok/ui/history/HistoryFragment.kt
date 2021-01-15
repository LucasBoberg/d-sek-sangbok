package se.dsek.sangbok.ui.history

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
import se.dsek.sangbok.ui.SongHistoryClickListener
import se.dsek.sangbok.ui.SongHistoryListAdapter
import se.dsek.sangbok.ui.song.SONG_ID
import se.dsek.sangbok.ui.song.SongActivity

class HistoryFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.application as SongApplication).songRepository)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val fastScrollView: FastScrollView = root.findViewById(R.id.song_fastscroll_view)
        val swipeRefreshLayout: SwipeRefreshLayout = root.findViewById(R.id.song_swipe)
        val songAdapter = SongHistoryListAdapter(SongHistoryClickListener { song, remove ->
            if (remove) {
                val updateSong = song.copy(lastViewed = 0)
                historyViewModel.updateSong(updateSong)
            } else {
                val intent = Intent(activity, SongActivity::class.java).apply {
                    putExtra(SONG_ID, song.id)
                }
                activity?.startActivity(intent)
            }
        })

        fastScrollView.setLayoutManager(LinearLayoutManager(context))
        fastScrollView.setAdapter(songAdapter)

        historyViewModel.historySongs.observe(viewLifecycleOwner, Observer { songs -> songs?.let {
            songAdapter.submitList(it)
            swipeRefreshLayout.isRefreshing = false
        }
        })

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
        }

        return root
    }
}