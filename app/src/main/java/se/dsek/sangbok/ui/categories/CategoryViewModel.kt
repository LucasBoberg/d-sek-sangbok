package se.dsek.sangbok.ui.categories

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.CategoryWithSongs
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class CategoryViewModel(private val repository: SongRepository) : ViewModel() {
    fun getCategoryWithSongs(id: Int): LiveData<CategoryWithSongs> = repository.getCategoryWithSongs(id)
    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }
}

class CategoryViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}