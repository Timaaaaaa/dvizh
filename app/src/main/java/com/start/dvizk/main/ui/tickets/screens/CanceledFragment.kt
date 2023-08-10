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
import com.start.dvizk.main.ui.tickets.model.MyCanceledTicket
import com.start.dvizk.main.ui.tickets.adapter.CanceledTicketsAdapter

class CanceledFragment : Fragment() {

	private lateinit var fragment_my_tickets_canceled_recycler: RecyclerView
	private lateinit var fragment_my_tickets_canceled_empty: ConstraintLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_my_tickets_canceled, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_my_tickets_canceled_recycler = view.findViewById(R.id.fragment_my_tickets_canceled_recycler)
		fragment_my_tickets_canceled_empty = view.findViewById(R.id.fragment_my_tickets_canceled_empty)

		val myCanceledTickets = listOf<MyCanceledTicket>(
			MyCanceledTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyCanceledTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyCanceledTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyCanceledTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
			MyCanceledTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы")
		)

		if (myCanceledTickets.isNotEmpty()) {
			fragment_my_tickets_canceled_empty.visibility = View.GONE
			fragment_my_tickets_canceled_recycler.visibility = View.VISIBLE
			val adapter = CanceledTicketsAdapter(myCanceledTickets)
			fragment_my_tickets_canceled_recycler.adapter = adapter
			fragment_my_tickets_canceled_recycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		} else {
			fragment_my_tickets_canceled_recycler.visibility = View.GONE
			fragment_my_tickets_canceled_empty.visibility = View.VISIBLE
		}
	}
}