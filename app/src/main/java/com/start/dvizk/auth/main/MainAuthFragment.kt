package com.start.dvizk.auth.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.auth.profile.ProfileAuthFragment
import com.start.dvizk.registration.registr.presentation.RegistrationFragment

class MainAuthFragment : Fragment() {

	private lateinit var registrationButton: TextView


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_main_auth, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		registrationButton = view.findViewById(R.id.text2)

		view.findViewById<Button>(R.id.button1).setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

			ft.add(R.id.nav_host_fragment_activity_main, ProfileAuthFragment())
			ft.addToBackStack(null)
			ft.commit()
		}

		registrationButton.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

			ft.add(R.id.nav_host_fragment_activity_main, RegistrationFragment())
			ft.addToBackStack(null)
			ft.commit()
		}
	}
}