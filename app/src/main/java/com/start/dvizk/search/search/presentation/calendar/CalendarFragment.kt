package com.start.dvizk.search.search.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.model.MyCanceledTicket
import com.start.dvizk.main.ui.tickets.adapter.CanceledTicketsAdapter
import java.util.*

class CalendarFragment : Fragment() {


	private lateinit var fragment_search_calendar_view: MaterialCalendarView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_calendar_search, container, false)
	}

	override fun onResume() {
		super.onResume()
		view?.requestLayout()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_search_calendar_view = view.findViewById(R.id.fragment_search_calendar_view)

		initCalendar(view)
	}

	fun initCalendar(view: View) {
		fragment_search_calendar_view = view.findViewById(R.id.fragment_search_calendar_view)

		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH)
		val day = currentDate.get(Calendar.DAY_OF_MONTH)

		val minDate = CalendarDay.from(year, month, day)
		fragment_search_calendar_view.state().edit()
			.setMinimumDate(minDate)
			.commit()

		fragment_search_calendar_view.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)

		fragment_search_calendar_view.setHeaderTextAppearance(R.style.CalendarHeader)
		fragment_search_calendar_view.setWeekDayTextAppearance(R.style.CalendarWeekDay)
		fragment_search_calendar_view.setDateTextAppearance(R.style.CalendarDate)
		fragment_search_calendar_view.selectionColor = R.color.purple_200

		fragment_search_calendar_view.setOnDateChangedListener { widget, date, selected ->
			if (selected) {


			}
		}
	}
}