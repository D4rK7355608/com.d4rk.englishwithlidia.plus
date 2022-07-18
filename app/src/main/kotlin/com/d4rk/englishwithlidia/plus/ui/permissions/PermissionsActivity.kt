package com.d4rk.englishwithlidia.plus.ui.permissions
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.PermissionsActivityBinding
class PermissionsActivity : AppCompatActivity() {
    private lateinit var binding: PermissionsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PermissionsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().replace(R.id.permissions, SettingsFragment()).commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.permissions_preferences, rootKey)
        }
    }
}