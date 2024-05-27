package com.moronlu18.invoice

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.snackbar.Snackbar
import com.moronlu18.invoice.databinding.ActivityMainBinding
import com.moronlu18.invoice.ui.utils.showToast

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    val toolbar: Toolbar get() = binding.contentMain.toolbar
    val drawer: DrawerLayout
        get() = binding.drawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.contentMain.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navController = navHostFragment.navController
        appBarConfiguration =
            AppBarConfiguration.Builder(navController.graph).setOpenableLayout(binding.drawerLayout)
                .build()
        setupActionBarWithNavController(navController, appBarConfiguration)

        /*binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }*/
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (!it) {
                    Snackbar.make(
                        findViewById<View>(android.R.id.content).rootView,
                        "Please grant Notification permission from App Settings",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        NavigationUI.setupWithNavController(
            binding.contentMain.toolbar,
            navController,
            appBarConfiguration
        )
        setupNavigationView()
    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else // dejamos que el SO haga su funcion
            super.onBackPressed()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                //La navegación se realiza directamente utilizando el id del fragment
                navController.navigate(R.id.settingsFragment)
                true
            }
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun setupNavigationView() {
        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_customer -> {
                    navController.navigate(R.id.action_mainFragment_to_itemFragment)
                }
                else -> {
                    showToast("Opción invalida")
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}