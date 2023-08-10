package com.start.dvizk.main.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.adapter.MyTicketsPagerAdapter

class MyTicketsFragment : Fragment() {

	private lateinit var fragment_my_tickets_tabs: TabLayout
	private lateinit var fragment_my_tickets_pager: ViewPager

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
		val fm: FragmentManager = requireActivity().supportFragmentManager
		val viewPagerAdapter = MyTicketsPagerAdapter(fm)
		fragment_my_tickets_pager.adapter = viewPagerAdapter

		fragment_my_tickets_tabs = view.findViewById(R.id.fragment_my_tickets_tabs)
		fragment_my_tickets_tabs.setupWithViewPager(fragment_my_tickets_pager)
	}
}