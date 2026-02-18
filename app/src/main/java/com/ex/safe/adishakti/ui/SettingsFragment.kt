package com.ex.safe.adishakti.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.ex.safe.adishakti.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val hardwareTrigger =
            findPreference<SwitchPreferenceCompat>("hardware_trigger")

        hardwareTrigger?.setOnPreferenceChangeListener { _, newValue ->

            if (newValue == true) {
                showConsentDialog()
                false
            } else {
                true
            }
        }
    }

    private fun showConsentDialog() {

        AlertDialog.Builder(requireContext())
            .setTitle("Emergency Hardware Trigger")
            .setMessage(
                "AdiShakti uses Accessibility Service ONLY for emergency trigger.\n\n" +
                        "No screen data is read.\n" +
                        "No personal data collected.\n" +
                        "Used only for safety."
            )
            .setPositiveButton("Enable") { _, _ ->
                startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
