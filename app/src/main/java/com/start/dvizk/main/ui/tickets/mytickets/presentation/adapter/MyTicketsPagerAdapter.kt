package com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.start.dvizk.main.ui.tickets.mytickets.presentation.screens.CanceledFragment
import com.start.dvizk.main.ui.tickets.mytickets.presentation.screens.FinishedFragment
import com.start.dvizk.main.ui.tickets.mytickets.presentation.screens.UpcomingFragment

class MyTicketsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

	override fun getItemCount(): Int = 3

	override fun createFragment(position: Int): Fragment {
		return when (position) {
			0 -> UpcomingFragment()
			1 -> FinishedFragment()
			2 -> CanceledFragment()
			else -> UpcomingFragment()
		}
	}

}