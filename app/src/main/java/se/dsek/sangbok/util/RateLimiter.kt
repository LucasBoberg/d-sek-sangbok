package se.dsek.sangbok.util

import android.os.SystemClock
import androidx.collection.ArrayMap
import java.util.concurrent.TimeUnit

const val SONGS_REFRESH_TIMESTAMP_KEY = "songs_refresh_timeout"

class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    
    private val timestamps = ArrayMap<KEY, Long>()
    private var timeout = timeUnit.toMillis(timeout.toLong())

    @Synchronized
    fun shouldFetch(key: KEY): Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null || lastFetched.equals(0)) {
            timestamps[key] = now
            return true
        }
        if (now - lastFetched > timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }
}