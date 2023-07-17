package com.start.dvizk.search.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.start.dvizk.search.search.presentation.calendar.CalendarFragment
import com.start.dvizk.search.search.presentation.calendar.CustomDateFragment

class SearchCalendarPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

	override fun getItemCount(): Int {
		return 2
	}

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> CalendarFragment()
			1 -> CustomDateFragment()
			else -> CalendarFragment()
		}
	}


}