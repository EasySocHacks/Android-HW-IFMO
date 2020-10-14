package ru.easy.soc.hacks.hw5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.easy.soc.hacks.hw5.extensions.setupWithNavController

var selectedBottomNavigationIdemId :Int = R.id.first_navigation

class MainActivity : AppCompatActivity() {
    private val SELECTED_BOTTOM_NAVIGATION_ITEM_ID_KEY = "SelectedBottomNavigationItemId"
    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation(savedInstanceState)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupBottomNavigation(savedInstanceState: Bundle?) {
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

        val selectedItemIdFromBundle = savedInstanceState?.getInt(SELECTED_BOTTOM_NAVIGATION_ITEM_ID_KEY)

        if (selectedItemIdFromBundle != 0 && selectedItemIdFromBundle != null) {
            bottomNavigationView.selectedItemId = selectedItemIdFromBundle
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            super.onBackPressed()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SELECTED_BOTTOM_NAVIGATION_ITEM_ID_KEY, selectedBottomNavigationIdemId)

        super.onSaveInstanceState(outState)
    }
}