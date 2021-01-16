package se.dsek.sangbok

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class SplashViewModel(private val repository: SongRepository) : ViewModel() {
    fun refresh() = viewModelScope.launch { repository.refresh() }
}

class SplashViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}