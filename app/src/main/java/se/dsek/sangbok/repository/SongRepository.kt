package se.dsek.sangbok.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import se.dsek.sangbok.api.JSONConverter
import se.dsek.sangbok.api.SongApiClient
import se.dsek.sangbok.db.*
import se.dsek.sangbok.util.RateLimiter
import se.dsek.sangbok.util.SONGS_REFRESH_TIMESTAMP_KEY

class SongRepository(
    private val songDao: SongDao
) {
    var error = MutableLiveData<String>()
    fun updateError(newError: String) = error.postValue(newError);

    val allSongs: LiveData<List<Song>> = songDao.getAlphabetizedSongs()

    val favoriteSongs: LiveData<List<Song>> = songDao.getFavoriteSongs()

    val historySongs: LiveData<List<Song>> = songDao.getHistorySongs()

    fun getSingleSong(id: Int): LiveData<Song> = songDao.getSingleSong(id)

    val allCategories: LiveData<List<Category>> = songDao.getAlphabetizedCategories()

    fun getCategoriesWithSongs(): LiveData<List<CategoryWithSongs>> = songDao.getCategoriesWithSongs()

    fun getCategoryWithSongs(id: Int): LiveData<CategoryWithSongs> = songDao.getCategoryWithSongs(id)

    suspend fun search(query: String): List<SongWithMatchInfo> = songDao.search(query)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateSong(song: Song) {
        songDao.updateSong(song)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertSong(song: Song) {
        songDao.insertSong(song)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertCategory(category: Category) {
        songDao.insertCategory(category)
    }


    suspend fun refresh() {
        try {
            Log.d("DEBUG", "Refresh songs")
            val categoryBody = SongApiClient.apiInterface.getCategories()
            val categoryList: List<Category> = JSONConverter.getAllCategories(categoryBody)
            for (category in categoryList) {
                insertCategory(category)
            }
            val songBody = SongApiClient.apiInterface.getSongs()
            val songList: List<Song> = JSONConverter.getAllSongs(songBody)
            for (song in songList) {
                insertSong(song)
            }
        } catch (cause: Throwable) {
            // throw Error("Unable to refresh songs", cause)
            error.postValue("Unable to refresh songs")
        }
    }
}