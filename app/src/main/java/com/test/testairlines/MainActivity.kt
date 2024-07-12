package com.test.testairlines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.home.HomeFragment

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.nav_home
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> return openFragment(HomeFragment())
            R.id.nav_hotel -> return openFragment(HomeFragment())
            R.id.nav_placemark -> return openFragment(HomeFragment())
            R.id.nav_notification -> return openFragment(HomeFragment())
            R.id.nav_profile -> return openFragment(HomeFragment())
        }
        return false
    }

    private fun openFragment(fragment: Fragment): Boolean{
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_fragment, fragment)
            .commit()
        return true
    }
}