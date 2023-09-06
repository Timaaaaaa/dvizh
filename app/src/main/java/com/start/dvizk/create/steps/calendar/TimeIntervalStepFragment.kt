package com.start.dvizk.create.steps.calendar

import android.app.Activity
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.SPECIFIC_DATA_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NAME
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.BottomSheetSelectListFragment
import com.start.dvizk.create.steps.bottomsheet.universal.IS_MULTI_SELECT_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.OnBottomSheetDismissListener
import com.start.dvizk.create.steps.bottomsheet.universal.SELECT_LIST_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class TimeIntervalStepFragment : Fragment(), OnSelectTimeListener, OnBottomSheetDismissListener {

	private val viewModel: TimeIntervalStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var next: Button
	private lateinit var back: Button

	lateinit var adapter: EventDateTimeIntervalAdapter
	private var listDate = mutableListOf<String>()
	private var listTimeInterval = mutableListOf<EventDateTimeInterval>()
	private lateinit var timeIntervalRecyclerView: RecyclerView
	var deadlineTimesListForRequest = mutableListOf<SelectItem>()
	private lateinit var currentInterval: EventDateTimeInterval
	var screenName = ""

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_time_interval_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initData()
		initDataTime()
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
				val imm: InputMethodManager =
					context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
				imm.hideSoftInputFromWindow(view?.windowToken, 0)
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

	private fun initView(view: View) {

		timeIntervalRecyclerView = view.findViewById(R.id.fragment_time_interval_step_List)

		timeIntervalRecyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		adapter = EventDateTimeIntervalAdapter(resources)
		adapter.setListener(this)
		timeIntervalRecyclerView.adapter = adapter
		screenName = arguments?.getString(STEP_NAME).toString()
		arguments?.getStringArrayList(SPECIFIC_DATA_KEY)?.toMutableList()?.let {
			listDate.addAll(it)
			adapter.setData(mapListToTimeInterval(listDate))
		}
		val headerBack: ImageView = view.findViewById(R.id.fragment_create_organization_back_image)
		headerBack.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)

		next.setOnClickListener {
			arguments?.apply {
				listTimeInterval.forEach {
					if (it.type == "datetimeSingle") {
						if (it.price == -1) {
							Toast.makeText(requireContext(), "Введите цену", Toast.LENGTH_LONG)
								.show()
							return@setOnClickListener
						}
						if (it.ticketCount == -1) {
							Toast.makeText(requireContext(), "Введите количество билетов", Toast.LENGTH_LONG)
								.show()
							return@setOnClickListener
						}
					} else {
						if (it.price == -1) {
							Toast.makeText(requireContext(), "Введите цену", Toast.LENGTH_LONG)
								.show()
							return@setOnClickListener
						}
						if (it.teamCount == -1) {
							Toast.makeText(requireContext(), "Введите количество команд", Toast.LENGTH_LONG)
								.show()
							return@setOnClickListener
						}
						if (it.teamMemberCount == -1) {
							Toast.makeText(requireContext(), "Введите количество участников для одной команды", Toast.LENGTH_LONG)
								.show()
							return@setOnClickListener
						}
					}
				}
				viewModel.sendEventDate(
					token = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					dateList = listTimeInterval
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun mapListToTimeInterval(listDate: MutableList<String>): MutableList<EventDateTimeInterval> {
		if (screenName == "datetimeSingle") {
			listDate.forEach {
				listTimeInterval.add(EventDateTimeInterval(
					date = it,
					startTime = "",
					duration = "",
					durationViewText = "",
					type = "datetimeSingle"
				))
			}
		} else {
			listDate.forEach {
				listTimeInterval.add(EventDateTimeInterval(
					date = it,
					startTime = "",
					duration = "",
					durationViewText = "",
					type = "datetimeGroup"
				))
			}
		}
		return listTimeInterval
	}

	override fun onStartTimeSelect(item: EventDateTimeInterval) {
		showTimePickerDialog(item, "start")
	}

	var deadlineTimesList = mutableListOf<SelectItem>()

	override fun onDurationTimeSelect(item: EventDateTimeInterval) {
		currentInterval = item
		deadlineTimesList.forEach {
			it.isSelect = false
		}
		val bottomSheetFragment = BottomSheetSelectListFragment()
		bottomSheetFragment.setListener(this)
		val args = Bundle()
		args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(deadlineTimesList))
		args.putBoolean(IS_MULTI_SELECT_KEY, false)
		args.putString("TITLE", "Выберите время")
		bottomSheetFragment.arguments = args
		bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
	}

	private var selectedTime: Calendar = Calendar.getInstance()

	private fun showTimePickerDialog(item: EventDateTimeInterval, type: String) {
		val hour = selectedTime.get(Calendar.HOUR_OF_DAY)
		val minute = selectedTime.get(Calendar.MINUTE)
		var formattedTime = ""
		val timePickerDialog = TimePickerDialog(
			context,
			TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
				selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
				selectedTime.set(Calendar.MINUTE, selectedMinute)

				val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
				formattedTime = timeFormat.format(selectedTime.time)
				if (type == "start") {
					item.startTime = formattedTime
					adapter.notifyDataSetChanged()
				}
			},
			hour,
			minute,
			DateFormat.is24HourFormat(activity)
		)

		timePickerDialog.show()

	}

	override fun onBottomSheetDismiss(
		ids: List<Int>,
		parameterName: String,
		list: MutableList<SelectItem>
	) {

		deadlineTimesList.forEach { time ->
			time.isSelect = false
			ids.forEach {
				if (time.id == it) {
					time.isSelect = true
					currentInterval.durationViewText = time.name
					currentInterval.duration = deadlineTimesListForRequest.find { it.id == time.id }?.name
				}
			}
		}

		adapter.notifyDataSetChanged()
		if (ids.isEmpty()) {
			currentInterval.durationViewText = ""

			return
		}
	}

	fun initData() {
		for (i in 1..23) {
			val name = if (i == 1) "$i час" else "$i часа"
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
}