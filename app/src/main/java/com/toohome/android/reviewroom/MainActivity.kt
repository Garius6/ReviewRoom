package com.toohome.android.reviewroom

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.movieListFragment -> {
                    navController.navigate(R.id.movieListFragment)
                }
                R.id.collectionsFragment -> {
                    navController.navigate(R.id.collectionsFragment)
                }
            }
        }
    }
}
