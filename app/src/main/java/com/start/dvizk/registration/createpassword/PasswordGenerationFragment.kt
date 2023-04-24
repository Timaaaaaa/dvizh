package com.start.dvizk.registration.createpassword

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.start.dvizk.R
import com.start.dvizk.main.MainActivity


class PasswordGenerationFragment :
	Fragment(),
	OnClickListener {

	private lateinit var continueRegistration: Button

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_password_generation, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)


		continueRegistration = view.findViewById(R.id.fragment_password_generation_continue_button)
		continueRegistration.setOnClickListener(this)
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			continueRegistration.id -> {
				val intent = Intent(requireContext(), MainActivity::class.java)
				startActivity(intent)
			}
		}
	}
}