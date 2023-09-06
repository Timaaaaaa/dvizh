package com.start.dvizk.create.steps.booking

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.BottomSheetSelectListFragment
import com.start.dvizk.create.steps.bottomsheet.universal.IS_MULTI_SELECT_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.OnBottomSheetDismissListener
import com.start.dvizk.create.steps.bottomsheet.universal.SELECT_LIST_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingStepFragment : Fragment(), OnBottomSheetDismissListener {

	private val viewModel: BookingStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var fragment_registration_user_gender_spinner: TextView

	var deadlineTimesList = mutableListOf<SelectItem>()
	var deadlineTimesListForRequest = mutableListOf<SelectItem>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_booking_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initData()
		initDataTime()
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun initView(view: View) {
		val headerBack: ImageView = view.findViewById(R.id.fragment_create_organization_back_image)
		headerBack.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		fragment_registration_user_gender_spinner =
			view.findViewById(R.id.fragment_registration_user_gender_spinner)

		next.setOnClickListener {
			deadlineTimesList.forEach { deadLine ->
				if (deadLine.isSelect) {
					deadlineTimesListForRequest.forEach {
						if (deadLine.id == it.id) {
							arguments?.apply {
								viewModel.sendEventPurchaseDeadline(
									token = sharedPreferencesRepository.getUserToken(),
									numberStep = getInt(STEP_NUMBER_KEY),
									eventId = getInt(EVENT_ID_KEY),
									purchaseDeadline = it.name
								)
							}
						}
					}
				}
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_registration_user_gender_spinner.setOnClickListener {
			deadlineTimesList.forEach {
				it.isSelect = false
			}
			val bottomSheetFragment = BottomSheetSelectListFragment()
			bottomSheetFragment.setListener(this)
			val args = Bundle()
			args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(deadlineTimesList))
			args.putString("TITLE", "Выберите время")
			args.putBoolean(IS_MULTI_SELECT_KEY, false)
			bottomSheetFragment.arguments = args
			bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
		}
	}

	fun initData() {
		for (i in 1..23) {
			val name = if (i == 1) "$i час до начала времени" else "$i часа до начала времени"
			val selectItem = SelectItem(id = i, name = name, isSelect = false)
			deadlineTimesList.add(selectItem)
		}
	}

	fun initDataTime() {
		for (hour in 1..23) {
			val formattedHour =
				hour.toString().padStart(2, '0') // Добавляем ведущий ноль, если час однозначный
			val time = "$formattedHour:00:00"
			deadlineTimesListForRequest.add(SelectItem(id = hour, name = time, isSelect = false))
		}
	}

	override fun onBottomSheetDismiss(
		ids: List<Int>,
		parameterName: String,
		list: MutableList<SelectItem>
	) {

		deadlineTimesList.forEach { lang ->
			lang.isSelect = false
			ids.forEach {
				if (lang.id == it) {
					lang.isSelect = true
					fragment_registration_user_gender_spinner.text = lang.name
				}
			}
		}
		if (ids.isEmpty()) {
			fragment_registration_user_gender_spinner.text = ""
			fragment_registration_user_gender_spinner.hint = "Выбрать время"

			return
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