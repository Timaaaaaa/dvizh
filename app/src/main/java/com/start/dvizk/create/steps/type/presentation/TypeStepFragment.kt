package com.start.dvizk.create.steps.type.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class TypeStepFragment : Fragment() {

	private val viewModel: TypeStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var privateTypeView: View
	private lateinit var publicTypeView: View

	private var type: String = ""

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_type_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()
	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_type_step_next)
		back = view.findViewById(R.id.fragment_type_step_back)
		privateTypeView = view.findViewById(R.id.view2)
		publicTypeView = view.findViewById(R.id.view1)

		next.setOnClickListener {

			if (type.isEmpty()) {
				Toast.makeText(requireContext(), "Не выбран тип события", Toast.LENGTH_SHORT).show()

				return@setOnClickListener
			}

			arguments?.apply {
				viewModel.sendEventType(
					token = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					type = type
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		publicTypeView.setOnClickListener {
			privateTypeView.background =
				ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_event_default_grey)
			publicTypeView.background = ContextCompat.getDrawable(
				requireContext(),
				R.drawable.bg_purple_rounded_corners_fill_dark_grey
			)
			type = "public"
		}
		privateTypeView.setOnClickListener {
			publicTypeView.background =
				ContextCompat.getDrawable(requireContext(), R.drawable.bg_item_event_default_grey)
			privateTypeView.background = ContextCompat.getDrawable(
				requireContext(),
				R.drawable.bg_purple_rounded_corners_fill_dark_grey
			)
			type = "private"
		}
	}

	private fun initObserver() {
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)
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