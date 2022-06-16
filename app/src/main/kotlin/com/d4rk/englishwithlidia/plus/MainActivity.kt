package com.d4rk.englishwithlidia.plus
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.d4rk.englishwithlidia.plus.databinding.ActivityMainBinding
import com.d4rk.englishwithlidia.plus.ui.feedback.FeedbackActivity
import com.google.android.material.navigation.NavigationView
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("UNUSED_VARIABLE")
        val splashScreen = installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController by lazy {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
            navHostFragment.navController
        }
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_about, R.id.nav_lessons), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.mobile_navigation)
        val darkModeValues = resources.getStringArray(R.array.dark_mode_values)
        when (PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.dark_mode), getString(R.string.dark_mode_def_value))) {
            darkModeValues[0] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            darkModeValues[1] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            darkModeValues[2] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            darkModeValues[3] -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.more_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.privacy_policy -> {
            val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/englishwithlidia/more/privacy-policy"))
            startActivity(newIntent)
            true
        }
        R.id.code_of_conduct -> {
            val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608/more/code-of-conduct"))
            startActivity(newIntent)
            true
        }
        R.id.terms_of_service -> {
            val newIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608/more/code-of-conduct"))
            startActivity(newIntent)
            true
        }
        R.id.feedback -> {
            val intent = Intent(this, FeedbackActivity::class.java)
            startActivity(intent)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController by lazy {
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
            navHostFragment.navController
        }
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}