package com.example.delivibes

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class Menu : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
//    private lateinit var context: Context //Para almecenar el contexto de la actividad actual
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar:androidx.appcompat.widget.Toolbar=findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

        drawer= findViewById(R.id.drawer_layout)
        toggle= ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)

        val navigationView:NavigationView= findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
//        context = this // Almacena una referencia al contexto de la actividad actual

    if (savedInstanceState == null) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, Fragment_PantallaInicio()).commit()
        navigationView.setCheckedItem(R.id.nav_home)
    }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when(item.itemId){
//                R.id.nave_item_three ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
//                R.id.nave_item_four ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
//                R.id.nave_item_five ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                R.id.nave_item_six -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, Fragment_Carta()).commit()

                R.id.nave_item_seven -> supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, Fragment_PantallaInicio()).commit()

//                R.id.nave_item_eight ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
//                R.id.nave_item_nine ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
//                R.id.nave_item_ten ->Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
            drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}