package com.start.dvizk.create.steps.price

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.calendar.TimeIntervalStepViewModel
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.discount.DiscountStepFragment
import com.start.dvizk.create.steps.eventrule.EventRuleStepFragment
import com.start.dvizk.create.steps.language.LanguageStepFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PriceStepFragment : Fragment() {

	private val viewModel: PriceStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var fragment_create_organization_button: Button
	private lateinit var price: EditText
	private lateinit var price2: EditText

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_price_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		fragment_create_organization_button =
			view.findViewById(R.id.fragment_create_organization_button)
		price = view.findViewById(R.id.asdzxc)
		price2 = view.findViewById(R.id.asdzxcw)

		price.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
				// Called before the text is changed
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				if (price.text.toString().isBlank() || price.text.toString().isEmpty()) {

				} else {
					price2.setText((price.text.toString().toInt() * 0.9).toInt().toString())
				}

			}

			override fun afterTextChanged(s: Editable?) {
				// Called after the text has been changed
			}
		})

		next.setOnClickListener {
			arguments?.apply {
				viewModel.sendEventPrice(
					token = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					price = price.text.toString().toInt(),
					commission = 1.1f,
					disList = listOf()
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_create_organization_button.setOnClickListener {
			val ft: FragmentTransaction =
				requireActivity().supportFragmentManager.beginTransaction()
			val fragment = DiscountStepFragment()
			ft.add(R.id.fragment_container, fragment)
			ft.addToBackStack(null)
			ft.commit()
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

				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(response.data.nextStep.name)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, response.data.nextStep.numberStep)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG)
			.show()
	}
}