package se.dsek.sangbok.ui.favorites

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class FavoritesViewModel(private val repository: SongRepository) : ViewModel() {

    val favoriteSongs: LiveData<List<Song>> = repository.favoriteSongs

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
}

class FavoritesViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}