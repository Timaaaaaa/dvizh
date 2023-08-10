package com.start.dvizk.main.ui.tickets.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.start.dvizk.main.ui.tickets.screens.CanceledFragment
import com.start.dvizk.main.ui.tickets.screens.FinishedFragment
import com.start.dvizk.main.ui.tickets.screens.UpcomingFragment

class MyTicketsPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

	override fun getItem(position: Int): Fragment {
		return when (position) {
			0 -> UpcomingFragment()
			1 -> FinishedFragment()
			2 -> CanceledFragment()
			else -> UpcomingFragment()
		}
	}

	override fun getCount(): Int {
		return 3
	}

	override fun getPageTitle(position: Int): CharSequence? {
		when (position) {
			0 -> return "Предстоящие"
			1 -> return "Завершенные"
			2 -> return "Отмененные"
		}
		return super.getPageTitle(position)
	}
}