package se.dsek.sangbok.ui.home

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class HomeViewModel(private val repository: SongRepository) : ViewModel() {
    val allSongs: LiveData<List<Song>> = repository.allSongs

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
    fun refresh() = viewModelScope.launch { repository.refresh() }
}

class HomeViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}