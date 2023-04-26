package com.start.dvizk.main.ui.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.FirstItemMarginDecoration
import com.start.dvizk.main.ui.home.presentation.model.PopularEvetsState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), OnItemClickListener {

	private val viewModel: HomeViewModel by viewModel()

	private lateinit var popularRecyclerView: RecyclerView
	private lateinit var categoryRecyclerView: RecyclerView

	private lateinit var popularAdapter: BigEventAdapter
	private lateinit var categoryAdapter: CategoryAdapter

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
	}

	override fun onItemClick(data: Event) {
		view?.let { Snackbar.make(it, "Скоро брат", Snackbar.LENGTH_LONG).show() }
	}

	private fun initView(view: View) {
		popularRecyclerView = view.findViewById(R.id.fragment_home_popular)
		categoryRecyclerView = view.findViewById(R.id.fragment_home_category_recycler_view)
	}

	private fun initPopularList() {
		popularRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		popularAdapter = BigEventAdapter(resources)
		popularRecyclerView.adapter = popularAdapter

		categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
		categoryAdapter = CategoryAdapter()
		categoryRecyclerView.adapter = categoryAdapter
		val firstItemOffset = resources.getDimensionPixelSize(R.dimen.first_item_offset)
		val subsequentItemOffset = resources.getDimensionPixelSize(R.dimen.subsequent_item_offset)
		val itemDecoration = FirstItemMarginDecoration(firstItemOffset, subsequentItemOffset)
		categoryRecyclerView.addItemDecoration(itemDecoration)
	}

	private fun initObserver() {
		viewModel.popularEventsStateLiveData.observe(viewLifecycleOwner, ::popularListInit)
		viewModel.categoriesListState.observe(viewLifecycleOwner, ::categoriesListInit)

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
}