package com.start.dvizk.main.ui.tickets

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.start.dvizk.R
import com.start.dvizk.databinding.FragmentNotificationsBinding
import com.start.dvizk.main.ui.notifications.Notification
import com.start.dvizk.main.ui.notifications.NotificationAdapter
import com.start.dvizk.main.ui.tickets.adapter.PagerAdapter

class MyTicketsFragment : Fragment() {

	private lateinit var fragment_my_tickets_pager: ViewPager
	private lateinit var fragment_my_tickets_tabs: TabLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val fm: FragmentManager = requireActivity().supportFragmentManager

		fragment_my_tickets_pager = view.findViewById(R.id.fragment_my_tickets_pager)
		val viewPagerAdapter = PagerAdapter(fm)
		fragment_my_tickets_pager.adapter = viewPagerAdapter

		fragment_my_tickets_tabs = view.findViewById(R.id.fragment_my_tickets_tabs)
		fragment_my_tickets_tabs.setupWithViewPager(fragment_my_tickets_pager)
	}
}