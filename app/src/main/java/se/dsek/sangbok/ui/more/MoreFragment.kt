package se.dsek.sangbok.ui.more

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import se.dsek.sangbok.R
import se.dsek.sangbok.SongApplication
import se.dsek.sangbok.ui.SongClickListener
import se.dsek.sangbok.ui.SongHistoryClickListener
import se.dsek.sangbok.ui.SongHistoryListAdapter
import se.dsek.sangbok.ui.SongListAdapter
import se.dsek.sangbok.ui.favorites.FavoritesViewModel
import se.dsek.sangbok.ui.favorites.FavoritesViewModelFactory
import se.dsek.sangbok.ui.history.MoreViewModel
import se.dsek.sangbok.ui.history.MoreViewModelFactory
import se.dsek.sangbok.ui.song.SONG_ID
import se.dsek.sangbok.ui.song.SongActivity

class MoreFragment : PreferenceFragmentCompat() {

    private val moreViewModel: MoreViewModel by viewModels {
        MoreViewModelFactory((activity?.application as SongApplication).songRepository)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.more, rootKey)
    }

}