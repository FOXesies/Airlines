package com.test.testairlines.presentation

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.test.core_navigation.util.UiMainEvent
import com.test.home.presentation.HomeFragment
import com.test.testairlines.R
import com.test.ticket.ticket.presentation.TicketsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var modelView: com.test.core_navigation.MainModelView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        modelView = ViewModelProvider(this)[com.test.core_navigation.MainModelView::class]

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.nav_home

        initObserver()
    }

    private fun initObserver(){
        modelView.uiEvent.observe(this){
            when(it){
                UiMainEvent.OpenHome -> openFragment(HomeFragment())
                UiMainEvent.OpenTicketPreview -> openFragment(TicketsFragment())
                UiMainEvent.OpenTicketList -> {}
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> modelView.setState(UiMainEvent.OpenHome)
            R.id.nav_hotel -> modelView.setState(UiMainEvent.OpenHome)
            R.id.nav_placemark -> modelView.setState(UiMainEvent.OpenHome)
            R.id.nav_notification -> modelView.setState(UiMainEvent.OpenHome)
            R.id.nav_profile -> modelView.setState(UiMainEvent.OpenHome)
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

    private var timestampBack = System.currentTimeMillis()
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (!modelView.isNavEmpty()){
            modelView.onBack()
        } else {
            if(timestampBack + 2000 > System.currentTimeMillis()) {
                super.onBackPressed()
                moveTaskToBack(true)
            }
            else {
                Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show()
            }
            timestampBack = System.currentTimeMillis()
        }
    }
}