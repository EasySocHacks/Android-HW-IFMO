package ru.easy.soc.hacks.hw5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.easy.soc.hacks.hw5.extensions.setupWithNavController
import ru.easy.soc.hacks.hw5.fragments.FirstFragment

class MainActivity : AppCompatActivity() {
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupBottomNavigation() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navGraphIds = listOf(
            R.navigation.first_navigation,
            R.navigation.second_navigation,
            R.navigation.third_navigation
        )
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.my_nav_host_fragment,
            intent = intent
        )
        currentNavController = controller
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}