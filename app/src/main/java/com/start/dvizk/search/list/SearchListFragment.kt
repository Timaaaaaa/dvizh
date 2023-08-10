package com.start.dvizk.search.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.favorites.FavoriteEvent
import com.start.dvizk.main.ui.favorites.FavoriteEventAdapter
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.search.search.adapter.SearchEventAdapter

class SearchListFragment : Fragment() {

	private lateinit var search_result_list_header_back: ImageView

	private lateinit var fragment_favorites_events_recycler_view: RecyclerView
	private lateinit var fragment_favorites_subtitle: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_search_result_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		search_result_list_header_back = view.findViewById(R.id.search_result_list_header_back)
		search_result_list_header_back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_favorites_subtitle = view.findViewById(R.id.fragment_favorites_subtitle)
		fragment_favorites_events_recycler_view = view.findViewById(R.id.fragment_favorites_events_recycler_view)
		fragment_favorites_events_recycler_view.layoutManager = LinearLayoutManager(view.context)

		val dataList = arguments?.getParcelableArrayList<Event>("SEARCH_RESULT_LIST")
		val total = arguments?.getInt("SEARCH_INT")

		fragment_favorites_subtitle.text = "Найдено: " + total

		val favoriteEventAdapter = dataList?.toMutableList()
			?.let { SearchEventAdapter(it, resources) }
		fragment_favorites_events_recycler_view.adapter = favoriteEventAdapter
	}
}