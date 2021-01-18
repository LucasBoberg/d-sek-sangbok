package se.dsek.sangbok

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    private val splashViewModel: SplashViewModel by viewModels {
        SplashViewModelFactory((application as SongApplication).songRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        val firstRun: Boolean = sharedPref.getBoolean(getString(R.string.first_run), true)

        if (firstRun) {
            sharedPref.edit().putBoolean(getString(R.string.first_run), false).apply()
            splashViewModel.refresh()
        }

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}