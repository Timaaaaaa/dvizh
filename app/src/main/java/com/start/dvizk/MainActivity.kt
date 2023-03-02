package com.start.dvizk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.auth.MainAuthFragment

class MainActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(layoutInflater.inflate(R.layout.activity_main, null, false))

		val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

		ft.replace(R.id.fragment_container, MainAuthFragment())

		ft.commit()
	}
}