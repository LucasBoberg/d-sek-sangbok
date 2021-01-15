package se.dsek.sangbok.ui.search

import android.text.Editable
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import se.dsek.sangbok.db.Song
import se.dsek.sangbok.db.SongFTS
import se.dsek.sangbok.db.calculateSearchScore
import se.dsek.sangbok.repository.SongRepository
import java.lang.IllegalArgumentException

class SearchViewModel(private val repository: SongRepository) : ViewModel() {
    private val _searchResults = MutableLiveData<List<Song>>()
    val searchResults: LiveData<List<Song>>
        get() = _searchResults

    fun updateSong(song: Song) = viewModelScope.launch { repository.updateSong(song) }

    fun search(query: String) {
        viewModelScope.launch {
            if (query.isNullOrBlank()) {

            } else {
                val sanitizedQuery = sanitizeSearchQuery(query)
                repository.search(sanitizedQuery).let { result ->
                    result.sortedByDescending { calculateSearchScore(it.matchInfo) }
                        .map { it.song }
                        .let { _searchResults.postValue(it) }
                }
            }
        }
    }

    private fun sanitizeSearchQuery(query: String): String {
        val queryWithEscapedQuotes = query.replace(Regex.fromLiteral("\""), "\"\"")
        return "*\"$queryWithEscapedQuotes\"*"
    }
}

class SearchViewModelFactory(private val repository: SongRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}