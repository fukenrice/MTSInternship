package com.example.mtsinternship.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mtsinternship.R
import com.example.mtsinternship.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setupActionBarWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_content))
                    .setNeutralButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp()
                || super.onSupportNavigateUp()
    }
}
