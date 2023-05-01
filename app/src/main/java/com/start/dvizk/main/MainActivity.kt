package com.start.dvizk.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.auth.main.MainAuthFragment
import com.start.dvizk.main.ui.dashboard.DashboardFragment
import com.start.dvizk.main.ui.home.presentation.HomeFragment
import com.start.dvizk.main.ui.notifications.NotificationsFragment
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

		val a = HomeFragment()
		val ft: FragmentTransaction = this.supportFragmentManager.beginTransaction()
		navView.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.navigation_home -> {
					Snackbar.make(view, "Скоро брат", Snackbar.LENGTH_LONG).show()

					ft.replace(R.id.nav_host_fragment_activity_main,a)
					ft.commit()

					true
				}
				R.id.navigation_dashboard -> {

					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
					ft.commit()

					true
				}
				R.id.navigation_notifications -> {

					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
					ft.commit()

					true
				}
				R.id.navigation_my_tickets -> {

					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
					ft.commit()

					true
				}
				R.id.navigation_profile -> {

					if (prefsRepository.getUserToken() == prefsRepository.no_value) {

						ft.replace(R.id.nav_host_fragment_activity_main, MainAuthFragment())
						ft.commit()

						return@setOnItemSelectedListener true
					}


					ft.replace(R.id.nav_host_fragment_activity_main, NotificationsFragment())
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