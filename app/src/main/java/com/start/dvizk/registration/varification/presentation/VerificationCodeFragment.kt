package com.start.dvizk.registration.varification.presentation

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.load.engine.Resource
import com.start.dvizk.R
import com.start.dvizk.main.MainPageFragment
import com.start.dvizk.registration.createpassword.PasswordGenerationFragment
import com.start.dvizk.registration.customview.CodeVerificationView
import com.start.dvizk.registration.registr.presentation.RegistrationState
import com.start.dvizk.registration.registr.presentation.RegistrationViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class VerificationCodeFragment :
		Fragment(),
		OnClickListener
{

	private val verificationViewModel: VerificationViewModel by viewModel<VerificationViewModel>()

	private lateinit var continueRegistration: Button
	private lateinit var codeVerificationView: CodeVerificationView
	private lateinit var fragment_registration_loader: ProgressBar

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_verification_code, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initObservers()

		val sendAgainTextView: TextView = view.findViewById(R.id.fragment_verification_code_error_sending_code)
		codeVerificationView = view.findViewById(R.id.fragment_verification_code_view)
		val text = "Мне не пришло сообщение с кодом.\n Отправить ещё раз"
		val spannableString = SpannableString(text)
		val colorSpan = ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.purple_500))
		spannableString.setSpan(colorSpan, 32, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		sendAgainTextView.text = spannableString

		fragment_registration_loader = requireActivity().findViewById(R.id.progressBarLarge)
		continueRegistration = view.findViewById(R.id.fragment_verification_code_continue_button)
		continueRegistration.setOnClickListener(this)
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			continueRegistration.id -> {

				val code = codeVerificationView.getCode()

				arguments?.getString("email")?.let {
					verificationViewModel.verify(
						it, code
					)
				}
			}
		}
	}

	private fun initObservers() {
		verificationViewModel.verificationStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: VerificationState) {
		when (state) {
			is VerificationState.Failed -> {
				fragment_registration_loader.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is VerificationState.Loading -> {
				fragment_registration_loader.visibility = View.VISIBLE
			}
			is VerificationState.Success -> {
				fragment_registration_loader.visibility = View.GONE

				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
				ft.add(R.id.fragment_container, PasswordGenerationFragment())
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}
}