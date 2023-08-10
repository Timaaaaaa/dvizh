package com.start.dvizk.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.search.search.presentation.SearchFragment

class SearchActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		window.navigationBarColor = ContextCompat.getColor(this, R.color.nav_bg)

		setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

		val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

		ft.replace(R.id.fragment_container, SearchFragment())

		ft.commit()
	}
}