package com.example.bubble.meizi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.bubble.meizi.R
import com.example.bubble.meizi.viewmodel.MeiziViewModel
import com.facebook.stetho.Stetho
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemReselectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnNavigationItemSelectedListener(this)
        bottom_navigation.selectedItemId = bottom_navigation.menu.getItem(0).itemId
        Stetho.initializeWithDefaults(applicationContext)
    }

    // bottom navigation switch event
    override fun onNavigationItemSelected(it: MenuItem): Boolean {
        when (it.itemId) {
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerId, ImageListFragment.newInstance())
                    .commit()
                Log.d("Meizi", "nav_home is selected")
                return true
            }
            R.id.nav_fav -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerId, FavoriteFragment.newInstance())
                    .commit()
                Log.d("Meizi", "nav_fav is selected")
                return true
            }
        }

        return false
    }

    override fun onNavigationItemReselected(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_home -> {
                Log.d("Meizi", "nav_home is reselected")
            }
            R.id.nav_fav -> {
                Log.d("Meizi", "nav_fav is reselected")
            }
        }

    }
}
