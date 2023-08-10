package com.start.dvizk.search.search.presentation.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.dvizk.main.ui.tickets.model.MyCanceledTicket
import com.start.dvizk.main.ui.tickets.adapter.CanceledTicketsAdapter
import com.start.dvizk.search.search.adapter.SearchCategoryAdapter
import com.start.dvizk.search.search.adapter.SearchCustomDayAdapter
import com.start.dvizk.search.search.presentation.SearchCategoryItemClick
import com.start.dvizk.search.search.presentation.SelectedParams
import com.start.dvizk.search.search.presentation.model.MonthModel
import java.text.SimpleDateFormat
import java.util.*

class CustomDateFragment : Fragment(), SearchCategoryItemClick {

	private lateinit var fragment_search_calendar_custom_days: RecyclerView

	private lateinit var categoryAdapter: SearchCustomDayAdapter

	private var listener: SelectedParams? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_custom_date, container, false)
	}

	override fun onResume() {
		super.onResume()
		view?.requestLayout()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_search_calendar_custom_days = view.findViewById(R.id.fragment_search_calendar_custom_days)

		fragment_search_calendar_custom_days.isNestedScrollingEnabled = false
		fragment_search_calendar_custom_days.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = SearchCustomDayAdapter(resources)
		listener?.let { categoryAdapter.setListener(it) }
		fragment_search_calendar_custom_days.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_search_calendar_custom_days.addItemDecoration(itemDecoration)

		categoryAdapter.
		setData(
			getMonthsList()
		)
	}

	override fun onCategoryItemClick(category: Category) {

	}

	fun getMonthsList(): List<MonthModel> {
		val monthsList = mutableListOf<MonthModel>()
		val calendar = Calendar.getInstance()

		// Получаем текущий месяц
		val currentMonth = calendar.get(Calendar.MONTH)
		val sdf = SimpleDateFormat("MMMM", Locale.getDefault())

		// Добавляем текущий месяц в список
		monthsList.add(
			MonthModel(
				monthName = sdf.format(calendar.time),
				year = calendar.get(Calendar.YEAR),
				monthNumber = calendar.get(Calendar.MONTH),
				isSelected = false,
				id = 0
			)
		)

		// Добавляем остальные месяцы в список
		for (i in 1 until 12) {
			calendar.add(Calendar.MONTH, 1)
			monthsList.add(
				MonthModel(
					monthName = sdf.format(calendar.time),
					year = calendar.get(Calendar.YEAR),
					monthNumber = calendar.get(Calendar.MONTH),
					id = i
				)
			)
		}

		return monthsList
	}

	fun setListener(listenerCustomDate: SelectedParams?){
		listener = listenerCustomDate
	}

}