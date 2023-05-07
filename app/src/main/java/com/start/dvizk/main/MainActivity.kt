package com.start.dvizk.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.auth.main.MainAuthFragment
import com.start.dvizk.create.CreateActivity
import com.start.dvizk.main.ui.home.presentation.HomeFragment
import com.start.dvizk.main.ui.profile.ProfileFragment
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

	val prefsRepository: SharedPreferencesRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		val view = layoutInflater.inflate(R.layout.activity_main, null, false)
		setContentView(view)

		val navView: BottomNavigationView = findViewById(R.id.nav_view)

		window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bg)

		println("---------------------"+SharedPreferencesRepository(this).getUserToken())
		println("---------------------"+SharedPreferencesRepository(this).getUserId())

		navView.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.navigation_home -> {
					val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
					val homeFragment = HomeFragment()
					ft.replace(R.id.nav_host_fragment_activity_main,homeFragment)
					ft.commit()

					true
				}
				R.id.navigation_favorites -> {

//					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
//					ft.commit()

					true
				}
				R.id.navigation_qr -> {
					val intent = Intent(this, CreateActivity::class.java)
					startActivity(intent)

					true
				}
				R.id.navigation_my_tickets -> {

//					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
//					ft.commit()

					true
				}
				R.id.navigation_profile -> {

					if (prefsRepository.getUserToken() == prefsRepository.no_value) {
						val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
						ft.replace(R.id.nav_host_fragment_activity_main, MainAuthFragment())
						ft.commit()

						return@setOnItemSelectedListener true
					}

					val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
					ft.replace(R.id.nav_host_fragment_activity_main, ProfileFragment())
					ft.commit()

					true
				}
				else -> {
					true
				}
			}
		}
	}
}