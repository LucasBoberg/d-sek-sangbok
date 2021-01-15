package se.dsek.sangbok.ui.about

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import se.dsek.sangbok.R

class AboutFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.about, rootKey)
    }

}