package com.start.dvizk.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.start.dvizk.R
import com.start.dvizk.registration.dialog.GenderSelectionDialog
import com.start.dvizk.registration.dialog.GenderSelectionListener

class RegistrationFragment : Fragment(), OnClickListener, GenderSelectionListener {

	private lateinit var genderSelectionText: TextView
	private lateinit var returnPage: ImageView

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_registration, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		genderSelectionText = view.findViewById(R.id.fragment_registration_user_gender_spinner)
		returnPage = view.findViewById(R.id.fragment_registration_return_button)
		returnPage.setOnClickListener(this)
		genderSelectionText.setOnClickListener(this)
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			genderSelectionText.id -> {
				val dialog = GenderSelectionDialog()
				dialog.setListener(this)
				dialog.show(requireActivity().supportFragmentManager, "GenderSelectionDialog")
			}
			returnPage.id -> {
				requireActivity().supportFragmentManager.popBackStack()
			}
		}
	}

	override fun getGender(gender: String) {
		genderSelectionText.text = gender
	}
}