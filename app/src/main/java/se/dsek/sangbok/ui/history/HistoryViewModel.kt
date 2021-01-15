package se.dsek.sangbok.ui.history

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class HistoryViewModel(private val repository: SongRepository) : ViewModel() {

    val historySongs: LiveData<List<Song>> = repository.historySongs

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
}

class HistoryViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HistoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}