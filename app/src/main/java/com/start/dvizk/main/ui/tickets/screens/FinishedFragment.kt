package com.start.dvizk.main.ui.tickets.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.tickets.model.MyFinishedTicket
import com.start.dvizk.main.ui.tickets.adapter.FinishedTicketsAdapter

class FinishedFragment : Fragment() {

	private lateinit var fragment_my_tickets_finished_recycler: RecyclerView
	private lateinit var fragment_my_tickets_finished_empty: ConstraintLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets_finished, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_my_tickets_finished_recycler = view.findViewById(R.id.fragment_my_tickets_finished_recycler)
		fragment_my_tickets_finished_empty = view.findViewById(R.id.fragment_my_tickets_finished_empty)

		val myFinishedTickets = listOf<MyFinishedTicket>(
			MyFinishedTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyFinishedTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyFinishedTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyFinishedTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы")
		)

		if (myFinishedTickets.isNotEmpty()) {
			fragment_my_tickets_finished_empty.visibility = View.GONE
			fragment_my_tickets_finished_recycler.visibility = View.VISIBLE
			val adapter = FinishedTicketsAdapter(myFinishedTickets)
			fragment_my_tickets_finished_recycler.adapter = adapter
			fragment_my_tickets_finished_recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		} else {
			fragment_my_tickets_finished_recycler.visibility = View.GONE
			fragment_my_tickets_finished_empty.visibility = View.VISIBLE
		}
	}
}