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

class CustomDateFragment : Fragment(), SearchCategoryItemClick {

	private lateinit var fragment_search_calendar_custom_days: RecyclerView

	private lateinit var categoryAdapter: SearchCustomDayAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_custom_date, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_search_calendar_custom_days = view.findViewById(R.id.fragment_search_calendar_custom_days)

		fragment_search_calendar_custom_days.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = SearchCustomDayAdapter(resources)
		categoryAdapter.setListener(this)
		fragment_search_calendar_custom_days.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_search_calendar_custom_days.addItemDecoration(itemDecoration)
		categoryAdapter.setData(listOf(
			Category(1,0,"Выходные","http://161.35.145.58/images/event_category/1676537107.jpg",  false),
			Category(2,0,"В этой неделе","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
			Category(3,0,"В этом месеце","http://161.35.145.58/images/event_category/1676878543.jpg",  false),
		))
	}

	override fun onItemClick(category: Category) {

	}
}