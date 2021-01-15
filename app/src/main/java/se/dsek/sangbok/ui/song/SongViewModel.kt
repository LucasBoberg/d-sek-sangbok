package se.dsek.sangbok.ui.song

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class SongViewModel(private val repository: SongRepository) : ViewModel() {
    fun getSong(id: Int): LiveData<Song> = repository.getSingleSong(id)

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
}

class SongViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}