package se.dsek.sangbok

import android.app.Application
import android.content.Context
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import se.dsek.sangbok.db.SongRoomDatabase
import se.dsek.sangbok.repository.SongRepository

class SongApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this);
        appContext = applicationContext
    }

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { SongRoomDatabase.getDatabase(this, applicationScope) }
    val songRepository by lazy {
        SongRepository(
            database.songDao()
        )
    }

    companion object {

        lateinit var appContext: Context

    }
}