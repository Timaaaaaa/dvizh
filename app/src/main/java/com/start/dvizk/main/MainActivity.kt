package com.start.dvizk.main

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.start.dvizk.R
import com.start.dvizk.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(layoutInflater.inflate(R.layout.activity_main, null, false))


		val navView: BottomNavigationView = findViewById(R.id.nav_view)

		val navController = findNavController(R.id.nav_host_fragment_activity_main)

		navView.setupWithNavController(navController)
		window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bg)
	}
}