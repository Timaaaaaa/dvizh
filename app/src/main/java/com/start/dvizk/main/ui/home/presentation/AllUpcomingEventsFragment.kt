package com.start.dvizk.main.ui.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.presentation.EventDetailsFragment
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.dvizk.main.ui.home.presentation.model.UpcomingEventsState
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllUpcomingEventsFragment : Fragment(), OnItemClickListener, OnCategoryItemClickListener {

	private val viewModel: HomeViewModel by viewModel()

	private lateinit var fragment_upcoming_events_page_return_button: ImageView

	private lateinit var fragment_upcoming_events_page_progress_bar: ProgressBar

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

	override fun onItemClick(data: Event) {
		val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
		val fragment = EventDetailsFragment()
		fragment.arguments = Bundle().apply {
			putInt(EVENT_ID, data.id.toInt())
		}
		ft.add(R.id.nav_host_fragment_activity_main, fragment)
		ft.addToBackStack("")
		ft.commit()
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initLists()
		initObserver()
	}

	private fun initView(view: View) {
		fragment_upcoming_events_page_return_button = view.findViewById(R.id.fragment_upcoming_events_page_return_button)
		fragment_upcoming_events_page_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_upcoming_events_page_category_recycler_view = view.findViewById(R.id.fragment_upcoming_events_page_category_recycler_view)
		fragment_upcoming_events_page_event_recycler_view = view.findViewById(R.id.fragment_upcoming_events_page_event_recycler_view)
	}

	private fun initLists() {
		fragment_upcoming_events_page_category_recycler_view.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = CategoryAdapter(resources)
		categoryAdapter.setListener(this)
		fragment_upcoming_events_page_category_recycler_view.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		fragment_upcoming_events_page_category_recycler_view.addItemDecoration(itemDecoration)

		fragment_upcoming_events_page_event_recycler_view.layoutManager =
			GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
		defaultEventAdapter = DefaultEventAdapter(resources)
		defaultEventAdapter.setListener(this)
		fragment_upcoming_events_page_event_recycler_view.adapter = defaultEventAdapter
	}

	private fun initObserver() {
		viewModel.categoriesListState.observe(viewLifecycleOwner, ::categoriesListInit)
		viewModel.upcomingEventsStateLiveData.observe(viewLifecycleOwner, ::upcomingListInit)
	}

	private fun categoriesListInit(state: CategoriesListState) {
		when (state) {
			is CategoriesListState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is CategoriesListState.Loading -> {

			}
			is CategoriesListState.Success -> {
				categoryAdapter.setData(state.categories)
			}
		}
	}

	private fun upcomingListInit(state: UpcomingEventsState) {
		when (state) {
			is UpcomingEventsState.Failed -> {
				fragment_upcoming_events_page_progress_bar.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is UpcomingEventsState.Loading -> {
				fragment_upcoming_events_page_progress_bar.visibility = View.VISIBLE
			}
			is UpcomingEventsState.Success -> {
				fragment_upcoming_events_page_progress_bar.visibility = View.GONE
				defaultEventAdapter.setData(state.events)
			}
		}
	}

	override fun onItemClick(data: Category) {
		viewModel.getUpcomingEvents(page = 1, categoryId = data.id.toInt())
	}
}