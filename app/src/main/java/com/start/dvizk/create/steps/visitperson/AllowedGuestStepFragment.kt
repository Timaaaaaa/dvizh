package com.start.dvizk.create.steps.visitperson

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.about.AboutStepViewModel
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.name.NameStepFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllowedGuestStepFragment : Fragment() {

	private val viewModel: AllowedGuestStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()


	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var fragment_create_organization_edit_text_1: EditText
	private lateinit var fragment_create_organization_edit_text_4: EditText
	private lateinit var fragment_allowed_guest_with_child_check_box: CheckBox

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_allowed_guest_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		fragment_create_organization_edit_text_1 = view.findViewById(R.id.fragment_create_organization_edit_text_1)
		fragment_allowed_guest_with_child_check_box = view.findViewById(R.id.fragment_allowed_guest_with_child_check_box)
		fragment_create_organization_edit_text_4 = view.findViewById(R.id.fragment_create_organization_edit_text_4)

		next.setOnClickListener {
			if(fragment_create_organization_edit_text_1.text.toString().isEmpty()) {
				Toast.makeText(requireContext(), "Введите возраст", Toast.LENGTH_LONG).show()

				return@setOnClickListener
			}
			arguments?.apply {
				viewModel.sendEventAllowedGuest(
					authorization = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					age = fragment_create_organization_edit_text_1.text.toString().toInt(),
					children = if(fragment_allowed_guest_with_child_check_box.isChecked) 1 else 0,
					additional_requirements = fragment_create_organization_edit_text_4.text.toString()
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? StepDataApiResponse ?: return responseFailed()
				val imm: InputMethodManager =
					context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
				imm.hideSoftInputFromWindow(view?.windowToken, 0)
				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(response.data.nextStep.name)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, response.data.nextStep.numberStep)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container,fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG).show()
	}
}