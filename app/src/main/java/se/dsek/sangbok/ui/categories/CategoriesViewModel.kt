package se.dsek.sangbok.ui.categories

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Category
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class CategoriesViewModel(private val repository: SongRepository) : ViewModel() {
    val allCategories: LiveData<List<Category>> = repository.allCategories
    fun refresh() = viewModelScope.launch { repository.refresh() }
}

class CategoriesViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}