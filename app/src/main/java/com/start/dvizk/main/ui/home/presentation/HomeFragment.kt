package com.start.dvizk.main.ui.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.main.ui.event.EventPageFragment
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.dvizk.main.ui.home.presentation.model.PopularEvetsState
import com.start.dvizk.main.ui.home.presentation.model.UpcomingEvetsState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), OnItemClickListener, OnCategoryItemClickListener {

	private val viewModel: HomeViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var popularRecyclerView: RecyclerView
	private lateinit var categoryRecyclerView: RecyclerView
	private lateinit var upcomingEventsRecyclerView: RecyclerView
	private lateinit var fragment_home_user_photo: ImageView
	private lateinit var fragment_home_upcoming_events_progress_bar: ProgressBar
	private lateinit var title: TextView

	private lateinit var popularAdapter: BigEventAdapter
	private lateinit var categoryAdapter: CategoryAdapter
	private lateinit var defaultEventAdapter: DefaultEventAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_home, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initPopularList()
		initObserver()
		viewModel.getPopularEvents()
		viewModel.getCategories()
		viewModel.getUpcomingEvents(1,1)
	}

	override fun onItemClick(data: Event) {
		view?.let { Snackbar.make(it, "Скоро брат", Snackbar.LENGTH_LONG).show() }

		val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

		ft.add(R.id.nav_host_fragment_activity_main, EventPageFragment())
		ft.addToBackStack("")
		ft.commit()
	}

	private fun initView(view: View) {
		title = view.findViewById(R.id.fragment_home_user_nickname)
		popularRecyclerView = view.findViewById(R.id.fragment_home_popular)
		categoryRecyclerView = view.findViewById(R.id.fragment_home_category_recycler_view)
		upcomingEventsRecyclerView = view.findViewById(R.id.fragment_home_upcoming_events_recycler_view)
		fragment_home_user_photo = view.findViewById(R.id.fragment_home_user_photo)
		fragment_home_upcoming_events_progress_bar = view.findViewById(R.id.fragment_home_upcoming_events_progress_bar)

		title.text = sharedPreferencesRepository.getUserName()
		Glide.with(this).load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMHyHnkeJyo587A_tb63tvSMvEy9USpStzZA&usqp=CAU").apply(RequestOptions.circleCropTransform()).into(fragment_home_user_photo)
	}

	private fun initPopularList() {
		popularRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		popularAdapter = BigEventAdapter(resources)
		popularAdapter.setListener(this)
		popularRecyclerView.adapter = popularAdapter

		categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = CategoryAdapter(resources)
		categoryAdapter.setListener(this)
		categoryRecyclerView.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		categoryRecyclerView.addItemDecoration(itemDecoration)

		upcomingEventsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
		defaultEventAdapter = DefaultEventAdapter(resources)
		upcomingEventsRecyclerView.adapter = defaultEventAdapter
	}

	private fun initObserver() {
		viewModel.popularEventsStateLiveData.observe(viewLifecycleOwner, ::popularListInit)
		viewModel.categoriesListState.observe(viewLifecycleOwner, ::categoriesListInit)
		viewModel.upcomingEventsStateLiveData.observe(viewLifecycleOwner, ::upcomingListInit)

	}

	private fun popularListInit(state: PopularEvetsState) {
		when (state) {
			is PopularEvetsState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is PopularEvetsState.Loading -> {
			}
			is PopularEvetsState.Success -> {
				popularAdapter.setData(state.events)
			}
		}
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

	private fun upcomingListInit(state: UpcomingEvetsState) {
		when (state) {
			is UpcomingEvetsState.Failed -> {
				fragment_home_upcoming_events_progress_bar.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is UpcomingEvetsState.Loading -> {
				fragment_home_upcoming_events_progress_bar.visibility = View.VISIBLE
			}
			is UpcomingEvetsState.Success -> {

				fragment_home_upcoming_events_progress_bar.visibility = View.GONE
				defaultEventAdapter.setData(state.events)
			}
		}
	}

	override fun onItemClick(data: Category) {
		viewModel.getUpcomingEvents(page = 1, categoryId = data.id.toInt())
	}
}