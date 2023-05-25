package com.start.dvizk.main.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category

class FavoritesFragment : Fragment() {

	private lateinit var fragment_favorites_category_recycler_view: RecyclerView
	private lateinit var fragment_favorites_events_recycler_view: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_favorites, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_favorites_category_recycler_view = view.findViewById(R.id.fragment_favorites_category_recycler_view)
		fragment_favorites_category_recycler_view.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
		fragment_favorites_events_recycler_view = view.findViewById(R.id.fragment_favorites_events_recycler_view)
		fragment_favorites_events_recycler_view.layoutManager = LinearLayoutManager(view.context)

		val categories = listOf(
			Category(1,2,"Все","",  true),
			Category(1,2,"Музыка","",  false),
			Category(1,2,"Спорт","",  false)
		)

		val favoriteEvents = listOf(
			FavoriteEvent(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы", true),
			FavoriteEvent(R.drawable.mafia, "Мафия и что где когда?", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы", true),
			FavoriteEvent(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы", true),
			FavoriteEvent(R.drawable.mafia, "Мафия и что где когда?", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы", true),
			FavoriteEvent(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы", false)
		)

		val categoryAdapter = CategoryAdapter(categories)
		fragment_favorites_category_recycler_view.adapter = categoryAdapter
		val favoriteEventAdapter = FavoriteEventAdapter(favoriteEvents)
		fragment_favorites_events_recycler_view.adapter = favoriteEventAdapter
	}
}