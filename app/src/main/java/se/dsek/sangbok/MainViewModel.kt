package se.dsek.sangbok

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class MainViewModel(private val repository: SongRepository) : ViewModel() {

    val error: LiveData<String> = repository.error
    fun updateError(newError: String) = viewModelScope.launch { repository.updateError(newError) }
}

class MainViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}