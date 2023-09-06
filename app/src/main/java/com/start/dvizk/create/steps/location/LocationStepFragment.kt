package com.start.dvizk.create.steps.location

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NAME
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.BottomSheetSelectListFragment
import com.start.dvizk.create.steps.bottomsheet.universal.IS_MULTI_SELECT_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.OnBottomSheetDismissListener
import com.start.dvizk.create.steps.bottomsheet.universal.PARAMETER_NAME_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.SELECT_LIST_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.language.model.EventParameter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationStepFragment : Fragment(), OnBottomSheetDismissListener {

	private val viewModel: LocationStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var countryText: TextView
	private lateinit var cityText: TextView
	private lateinit var streetText: EditText
	private lateinit var apartmentText: EditText
	private lateinit var descriptionText: EditText

	var countryList = listOf<EventParameter>()
	var cityList = listOf<EventParameter>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_location_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)

		viewModel.requestCountryResponseStateLiveData.observe(
			viewLifecycleOwner,
			::handleCountryState
		)
		viewModel.requestCityResponseStateLiveData.observe(viewLifecycleOwner, ::handleCityState)
		viewModel.requestSendLocationResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun initView(view: View) {
		val headerBack: ImageView = view.findViewById(R.id.fragment_create_organization_back_image)
		headerBack.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		countryText = view.findViewById(R.id.fragment_registration_user_gender_spinner)
		cityText = view.findViewById(R.id.fragment_location_step_city)
		streetText = view.findViewById(R.id.fragment_location_step_street)
		apartmentText = view.findViewById(R.id.fragment_location_step_flat)
		descriptionText = view.findViewById(R.id.fragment_create_organization_edit_text_1)

		next.setOnClickListener {
			if (countryList.isEmpty() || cityList.isEmpty()) {
				Toast.makeText(requireContext(), "Не все данные заполнены", Toast.LENGTH_LONG)
					.show()

				return@setOnClickListener
			}
			arguments?.apply {
				viewModel.sendEventDescription(
					country_id = countryList.first().id,
					city_id = cityList.first().id,
					apartment = if (apartmentText.text.toString().isEmpty()) null else apartmentText.text.toString().toInt(),
					street = streetText.text.toString(),
					authorization = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					description = descriptionText.text.toString()
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		countryText.setOnClickListener {

				viewModel.getCountry()

		}

		cityText.setOnClickListener {

				viewModel.getCity()

		}
	}

	private fun handleCountryState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				countryList = state.value as? List<EventParameter> ?: return responseFailed()
				var selectListCurrent = listOf<SelectItem>()
				countryList.forEach {
					selectListCurrent =
						selectListCurrent.plus(mapToSelectListItem(it.id, it.name, it.isSelected))
				}
				val bottomSheetFragment = BottomSheetSelectListFragment()
				bottomSheetFragment.setListener(this)
				val args = Bundle()
				args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(selectListCurrent))
				args.putString(PARAMETER_NAME_KEY, "country")
				args.putString("TITLE", "Выберите страну")
				args.putBoolean(IS_MULTI_SELECT_KEY, false)
				bottomSheetFragment.arguments = args
				bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
			}
		}
	}

	private fun handleCityState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				cityList = state.value as? List<EventParameter> ?: return responseFailed()
				var selectListCurrent = listOf<SelectItem>()
				cityList.forEach {
					selectListCurrent =
						selectListCurrent.plus(mapToSelectListItem(it.id, it.name, it.isSelected))
				}
				val bottomSheetFragment = BottomSheetSelectListFragment()
				bottomSheetFragment.setListener(this)
				val args = Bundle()
				args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(selectListCurrent))
				args.putString(PARAMETER_NAME_KEY, "city")
				args.putString("TITLE", "Выберите город")
				args.putBoolean(IS_MULTI_SELECT_KEY, false)
				bottomSheetFragment.arguments = args
				bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
			}
		}
	}

	fun mapToSelectListItem(id: Int, name: String, isSelected: Boolean): SelectItem {
		return SelectItem(id = id, name = name, isSelect = isSelected)
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG)
			.show()
	}

	override fun onBottomSheetDismiss(
		ids: List<Int>,
		parameterName: String,
		list: MutableList<SelectItem>
	) {
		when (parameterName) {
			"country" -> {
				var languagesNames = ""
				var secondLang = false
				countryList.forEach { lang ->
					lang.isSelected = false
					ids.forEach {
						if (lang.id == it) {

							lang.isSelected = true
							if (secondLang) {
								languagesNames += ", " + lang.name
							} else {
								languagesNames += lang.name
							}

							secondLang = true
						}
					}
				}
				countryText.text = languagesNames
			}
			"city" -> {
				var languagesNames = ""
				var secondLang = false
				cityList.forEach { lang ->
					lang.isSelected = false
					ids.forEach {
						if (lang.id == it) {

							lang.isSelected = true
							if (secondLang) {
								languagesNames += ", " + lang.name
							} else {
								languagesNames += lang.name
							}

							secondLang = true
						}
					}
				}
				cityText.text = languagesNames
			}
			else -> {
				print("Ошибка, попробуйте еще раз позже")
			}
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
					putString(STEP_NAME, response.data.nextStep.name)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container,fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}
}