package com.start.dvizk.create

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.OrgаnizationsListFragment
import org.koin.android.ext.android.inject

class CreateActivity : AppCompatActivity() {

	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (sharedPreferencesRepository.getFirstLaunchInstructio()) {
			sharedPreferencesRepository.setFirstLaunchInstructio(false)
			setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

			val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

			ft.replace(R.id.fragment_container, InstructionFragment())

			ft.commit()
		} else {
			setContentView(layoutInflater.inflate(R.layout.activity_auth, null, false))

			val ft: FragmentTransaction = supportFragmentManager.beginTransaction()

			ft.replace(R.id.fragment_container, OrgаnizationsListFragment())

			ft.commit()
		}

	}
}