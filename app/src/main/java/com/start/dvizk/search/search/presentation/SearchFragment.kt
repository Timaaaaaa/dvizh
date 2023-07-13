package com.start.dvizk.search.search.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.CategoryAdapter
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import java.util.*

class SearchFragment :
		Fragment(),
		OnClickListener,
		SearchCategoryItemClick
{

	private lateinit var fragment_bottom_sheet_category: View
	private lateinit var fragment_home_search_edit_text: View
	private lateinit var fragment_search_calendar_header: View
	private lateinit var fragment_search_category_list: RecyclerView
	private lateinit var calendarView: MaterialCalendarView

	private lateinit var fragment_search_calendar_next: Button
	private lateinit var fragment_create_organization_separator_1: View
	private lateinit var fragment_search_calendar_clear: TextView

	private lateinit var view_quest: View

	private lateinit var categoryAdapter: SearchCategoryAdapter

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_search, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_search_calendar_header = view.findViewById(R.id.fragment_search_calendar_header)
		fragment_home_search_edit_text = view.findViewById(R.id.fragment_home_search_edit_text)
		fragment_bottom_sheet_category = view.findViewById(R.id.fragment_bottom_sheet_category)
		fragment_search_category_list = view.findViewById(R.id.fragment_search_category_list)
		fragment_search_calendar_next = view.findViewById(R.id.fragment_search_calendar_next)
		fragment_create_organization_separator_1 = view.findViewById(R.id.fragment_create_organization_separator_1)
		fragment_search_calendar_clear = view.findViewById(R.id.fragment_search_calendar_clear)
		view_quest = view.findViewById(R.id.view_quest)
		initCalendar(view)

		fragment_search_category_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = SearchCategoryAdapter(resources)
		categoryAdapter.setListener(this)
		fragment_search_category_list.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_search_category_list.addItemDecoration(itemDecoration)
		categoryAdapter.setData(listOf(
			Category(1,0,"Все","http://161.35.145.58/images/event_category/1676537107.jpg",  false),
			Category(2,0,"Музыка","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
			Category(3,0,"Спорт","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
		))

		fragment_bottom_sheet_category.setOnClickListener(this)
		fragment_search_calendar_header.setOnClickListener(this)
		fragment_search_calendar_next.setOnClickListener(this)
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			fragment_bottom_sheet_category.id -> {
				fragment_home_search_edit_text.visibility = View.VISIBLE
				fragment_search_category_list.visibility = View.VISIBLE
			}
			fragment_search_calendar_header.id -> {
				fragment_home_search_edit_text.visibility = View.GONE
				fragment_search_category_list.visibility = View.GONE

				calendarView.visibility = View.VISIBLE
				fragment_create_organization_separator_1.visibility = View.VISIBLE
				fragment_search_calendar_clear.visibility = View.VISIBLE
				fragment_search_calendar_next.visibility = View.VISIBLE

			}
			fragment_search_calendar_next.id -> {
				calendarView.visibility = View.GONE
				fragment_create_organization_separator_1.visibility = View.GONE
				fragment_search_calendar_clear.visibility = View.GONE
				fragment_search_calendar_next.visibility = View.GONE
				view_quest.visibility = View.VISIBLE

			}
		}
	}

	override fun onItemClick(category: Category) {

	}

	fun initCalendar(view: View) {
		calendarView = view.findViewById(R.id.fragment_search_calendar_view)

		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH)
		val day = currentDate.get(Calendar.DAY_OF_MONTH)

		val minDate = CalendarDay.from(year, month, day)
		calendarView.state().edit()
			.setMinimumDate(minDate)
			.commit()

		calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_RANGE)

		calendarView.setHeaderTextAppearance(R.style.CalendarHeader)
		calendarView.setWeekDayTextAppearance(R.style.CalendarWeekDay)
		calendarView.setDateTextAppearance(R.style.CalendarDate)
		calendarView.selectionColor = R.color.purple_200

		calendarView.setOnDateChangedListener { widget, date, selected ->
			if (selected) {


			}
		}
	}
}