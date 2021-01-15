package se.dsek.sangbok.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Song::class, SongFTS::class, Category::class), version = 10, exportSchema = false)
public abstract class SongRoomDatabase : RoomDatabase() {

    abstract fun songDao(): SongDao

    private class SongDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.songDao())
                }
            }
        }

        suspend fun populateDatabase(songDao: SongDao) {
            // Used if wanting to seed data in the future
        }
    }

    companion object {

        // Singleton prevents multiple instances of database opening at the same time
        @Volatile
        private var INSTANCE: SongRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): SongRoomDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SongRoomDatabase::class.java,
                    "song_database"
                ).addCallback(SongDatabaseCallback(scope)).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}