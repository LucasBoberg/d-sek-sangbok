package se.dsek.sangbok.ui.history

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class MoreViewModel(private val repository: SongRepository) : ViewModel() {

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
}

class MoreViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MoreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}