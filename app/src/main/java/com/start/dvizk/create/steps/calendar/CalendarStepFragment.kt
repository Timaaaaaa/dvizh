package com.start.dvizk.create.steps.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.prolificinteractive.materialcalendarview.MaterialCalendarView


class CalendarStepFragment : Fragment() {

	private lateinit var next: Button
	private lateinit var back: Button

	private lateinit var calendarView: MaterialCalendarView

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

		next.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = TimeIntervalStepFragment()
			ft.add(R.id.fragment_container,fragment)
			ft.addToBackStack(null)
			ft.commit()
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		calendarView = view.findViewById(R.id.calendarView)

		calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_MULTIPLE)

		calendarView.setHeaderTextAppearance(R.style.CalendarHeader)
		calendarView.setWeekDayTextAppearance(R.style.CalendarWeekDay)
		calendarView.setDateTextAppearance(R.style.CalendarDate)
		calendarView.selectionColor = R.color.calendar_selector_color

		calendarView.setOnDateChangedListener { widget, date, selected ->
			if (selected) {
				val selectedDates = calendarView.selectedDates
				Toast.makeText(requireContext(), "" + selectedDates, Toast.LENGTH_LONG).show()
			} 
		}
	}
}