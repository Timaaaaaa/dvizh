package com.start.dvizk.search.search.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.start.dvizk.search.search.presentation.SelectedParams
import com.start.dvizk.search.search.presentation.calendar.CalendarFragment
import com.start.dvizk.search.search.presentation.calendar.CustomDateFragment

class SearchCalendarPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

	private var listener: SelectedParams? = null

	override fun getItemCount(): Int {
		return 2
	}

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> {
				val fragment = CalendarFragment()
				fragment.setListener(listener)
				return fragment
			}
			1 -> {
				val fragment = CustomDateFragment()
				fragment.setListener(listener)
				return fragment
			}
			else -> CalendarFragment()
		}
	}

	fun setListener(listenerCustomDate: SelectedParams?){
		listener = listenerCustomDate
	}
}