package com.start.dvizk.main.ui.home.presentation.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.CategoryAdapter
import com.start.dvizk.main.ui.home.presentation.DefaultEventAdapter

class AllUpcomingEventsFragment : Fragment() {

	private lateinit var fragment_upcoming_events_page_return_button: ImageView

	private lateinit var fragment_upcoming_events_page_category_recycler_view: RecyclerView
	private lateinit var fragment_upcoming_events_page_event_recycler_view: RecyclerView

	private lateinit var categoryAdapter: CategoryAdapter
	private lateinit var defaultEventAdapter: DefaultEventAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_upcoming_events_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_upcoming_events_page_return_button = view.findViewById(R.id.fragment_upcoming_events_page_return_button)
		fragment_upcoming_events_page_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_upcoming_events_page_category_recycler_view = view.findViewById(R.id.fragment_upcoming_events_page_category_recycler_view)
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_upcoming_events_page_category_recycler_view.addItemDecoration(itemDecoration)

		fragment_upcoming_events_page_event_recycler_view.layoutManager =
			GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
		defaultEventAdapter = DefaultEventAdapter(resources)
		fragment_upcoming_events_page_event_recycler_view.adapter = defaultEventAdapter


	}
}