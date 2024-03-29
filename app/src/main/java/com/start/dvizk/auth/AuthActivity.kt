package com.start.dvizk.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.auth.main.MainAuthFragment

class AuthActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

		val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

		ft.replace(R.id.fragment_container, MainAuthFragment())

		ft.commit()
	}
}