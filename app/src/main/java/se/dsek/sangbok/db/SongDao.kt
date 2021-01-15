package se.dsek.sangbok.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SongDao {
    @Query("SELECT * FROM song_table ORDER BY title ASC")
    fun getAlphabetizedSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM song_table WHERE favorite = 1 ORDER BY title ASC")
    fun getFavoriteSongs(): LiveData<List<Song>>

    @Query("SELECT * FROM song_table WHERE lastViewed > 0 ORDER BY lastViewed DESC")
    fun getHistorySongs(): LiveData<List<Song>>

    @Query("SELECT * FROM song_table WHERE id=:id")
    fun getSingleSong(id: Int): LiveData<Song>

    @Query("SELECT * FROM category_table ORDER BY title ASC")
    fun getAlphabetizedCategories(): LiveData<List<Category>>

    @Transaction
    @Query("SELECT * FROM category_table WHERE id=:id")
    fun getCategoryWithSongs(id: Int): LiveData<CategoryWithSongs>

    @Query("SELECT * FROM category_table WHERE id=:id")
    fun getSingleCategory(id: Int): LiveData<Category>

    @Transaction
    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun getCategoriesWithSongs(): LiveData<List<CategoryWithSongs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSong(song: Song)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Query("SELECT *, matchInfo(song_table_fts) as matchInfo FROM song_table JOIN song_table_fts ON song_table.id = song_table_fts.id WHERE song_table_fts MATCH :query")
    suspend fun search(query: String): List<SongWithMatchInfo>

    @Query("DELETE FROM song_table")
    suspend fun deleteAll()

}