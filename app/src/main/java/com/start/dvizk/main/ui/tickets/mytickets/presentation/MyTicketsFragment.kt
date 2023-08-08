package com.start.dvizk.main.ui.tickets.mytickets.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter.MyTicketsPagerAdapter

class MyTicketsFragment : Fragment() {

	private lateinit var fragment_my_tickets_tabs: TabLayout
	private lateinit var fragment_my_tickets_pager: ViewPager2

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		fragment_my_tickets_pager = view.findViewById(R.id.fragment_my_tickets_pager)
		val viewPagerAdapter = MyTicketsPagerAdapter(this)
		fragment_my_tickets_pager.adapter = viewPagerAdapter

		val tabTitles = listOf("Предстоящие", "Завершенные", "Отмененные")

		fragment_my_tickets_tabs = view.findViewById(R.id.fragment_my_tickets_tabs)
		TabLayoutMediator(fragment_my_tickets_tabs, fragment_my_tickets_pager) { tab, position ->
			tab.text = tabTitles[position]
		}.attach()
	}
}