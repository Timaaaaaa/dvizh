package com.start.dvizk.create.steps.calendar

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.start.dvizk.R
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.SPECIFIC_DATA_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NAME
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CalendarStepFragment : Fragment() {

	private lateinit var next: Button
	private lateinit var back: Button

	private lateinit var calendarView: MaterialCalendarView

	var dates = mutableListOf<String>()
	var selectedDates = mutableListOf<CalendarDay>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		val view = inflater.inflate(R.layout.fragment_calendar_step, container, false)
		initView(view)

		return view
	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		val headerBack: ImageView = view.findViewById(R.id.fragment_create_organization_back_image)
		headerBack.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		next.setOnClickListener {
			if (selectedDates.isEmpty()) {
				Toast.makeText(requireContext(), "Выберите день мероприятия", Toast.LENGTH_LONG).show()

				return@setOnClickListener
			}

			selectedDates.forEach {
				dates.add(mapCorectDateFormat(it))
			}

			arguments?.apply {
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = TimeIntervalStepFragment()
				val a = this.getInt(STEP_NUMBER_KEY)
				val b = this.getInt(EVENT_ID_KEY)
				val c = this.getString(STEP_NAME)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, a)
					putInt(EVENT_ID_KEY, b)
					putString(STEP_NAME, c)
					putStringArrayList(SPECIFIC_DATA_KEY,  ArrayList(dates.distinct()))
				}
				ft.add(R.id.fragment_container, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		calendarView = view.findViewById(R.id.calendarView)

		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH)
		val day = currentDate.get(Calendar.DAY_OF_MONTH)

		val minDate = CalendarDay.from(year, month, day)
		calendarView.state().edit()
			.setMinimumDate(minDate)
			.commit()

		calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE)

		calendarView.setHeaderTextAppearance(R.style.CalendarHeader)
		calendarView.setWeekDayTextAppearance(R.style.CalendarWeekDay)
		calendarView.setDateTextAppearance(R.style.CalendarDate)
		calendarView.selectionColor = R.color.purple_200

		calendarView.setOnDateChangedListener { widget, date, selected ->
			if (selected) {
				selectedDates = calendarView.selectedDates

			}
		}
	}

	fun mapCorectDateFormat(selectedDate: CalendarDay): String {
		val year = selectedDate.year
		val month = selectedDate.month
		val day = selectedDate.day

		val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
		val formattedDate = dateFormat.format(Calendar.getInstance().apply {
			set(year, month, day)
		}.time)

		return formattedDate
	}

//	private lateinit var timePickerButton: Button
//	private var selectedTime: Calendar = Calendar.getInstance()

//	private fun showTimePickerDialog() {
//		val hour = selectedTime.get(Calendar.HOUR_OF_DAY)
//		val minute = selectedTime.get(Calendar.MINUTE)
//
//		val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
//			selectedTime.set(Calendar.HOUR_OF_DAY, selectedHour)
//			selectedTime.set(Calendar.MINUTE, selectedMinute)
//
//			val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//			val formattedTime = timeFormat.format(selectedTime.time)
//
//			timePickerButton.text = formattedTime
//		}, hour, minute, true)
//
//		timePickerDialog.show()
//	}
}