package com.example.xmlarchitectapp.activities

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.xmlarchitectapp.R
import com.example.xmlarchitectapp.fragments.HomeFragment
import com.example.xmlarchitectapp.fragments.ProductListFragment
import com.example.xmlarchitectapp.fragments.ProductDetailFragment
import com.example.xmlarchitectapp.models.Product
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * DashboardActivity - Main container activity (F1, F4).
 * Acts as a navigation coordinator hosting Fragments.
 * Receives data from LoginActivity via Intent Extras (F1).
 * Manages Fragment transactions without restarting the activity (F4).
 */
class DashboardActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var tvWelcome: TextView

    // Data received from LoginActivity via Intent Extras (F1)
    private var userName: String = ""
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // F1: Receiving data from Intent Extras
        receiveIntentData()

        // Initialize views
        tvWelcome = findViewById(R.id.tv_welcome)
        bottomNav = findViewById(R.id.bottom_navigation)

        // Display received data (F1)
        tvWelcome.text = "Welcome, $userName!"

        // F4: Set up bottom navigation for fragment switching
        setupBottomNavigation()

        // Load default fragment (F4)
        if (savedInstanceState == null) {
            loadFragment(HomeFragment.newInstance(userName, userEmail))
            bottomNav.selectedItemId = R.id.nav_home
        }
    }

    /**
     * F1: Extracts data passed from LoginActivity via Intent Extras.
     * Uses intent.getStringExtra() to retrieve data.
     */
    private fun receiveIntentData() {
        userName = intent.getStringExtra("USER_NAME") ?: "Guest"
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        val loginTime = intent.getLongExtra("LOGIN_TIME", 0L)

        if (loginTime > 0) {
            Toast.makeText(this, "Logged in as $userEmail", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * F4: Sets up BottomNavigationView to switch between fragments
     * without restarting the activity.
     */
    private fun setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment.newInstance(userName, userEmail))
                    true
                }
                R.id.nav_products -> {
                    loadFragment(ProductListFragment.newInstance(userName))
                    true
                }
                R.id.nav_profile -> {
                    // Reuse HomeFragment with profile focus
                    loadFragment(HomeFragment.newInstance(userName, userEmail))
                    Toast.makeText(this, "Profile: $userEmail", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }

    /**
     * F4: Loads a fragment into the container using FragmentTransaction.
     * Replaces current fragment without restarting the activity.
     */
    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * F2: Called by ProductListFragment to show product details.
     * Passes Product object via Bundle to ProductDetailFragment.
     */
    fun showProductDetail(product: Product) {
        val detailFragment = ProductDetailFragment.newInstance(product)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, detailFragment)
            .addToBackStack("product_detail")
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
