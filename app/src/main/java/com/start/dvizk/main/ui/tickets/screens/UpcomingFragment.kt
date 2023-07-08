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
import com.start.dvizk.main.ui.tickets.model.MyUpcomingTicket
import com.start.dvizk.main.ui.tickets.adapter.UpcomingTicketsAdapter

class UpcomingFragment : Fragment() {

	var myUpcomingTickets = mutableListOf<MyUpcomingTicket>(
		MyUpcomingTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
		MyUpcomingTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
		MyUpcomingTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы"),
		MyUpcomingTicket(R.drawable.dj, "Мастер класс по искусству", "24 Мар · 15:00 - 17:00", "Атакент парк, Алматы")
	)

	private lateinit var fragment_my_tickets_upcoming_recycler: RecyclerView
	private lateinit var fragment_my_tickets_upcoming_empty: ConstraintLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets_upcoming, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_my_tickets_upcoming_recycler =
			view.findViewById(R.id.fragment_my_tickets_upcoming_recycler)
		fragment_my_tickets_upcoming_empty =
			view.findViewById(R.id.fragment_my_tickets_upcoming_empty)



		if (myUpcomingTickets.isNotEmpty()) {
			fragment_my_tickets_upcoming_empty.visibility = View.GONE
			fragment_my_tickets_upcoming_recycler.visibility = View.VISIBLE
			val adapter = UpcomingTicketsAdapter(myUpcomingTickets)
			fragment_my_tickets_upcoming_recycler.adapter = adapter
			fragment_my_tickets_upcoming_recycler.layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		} else {
			fragment_my_tickets_upcoming_recycler.visibility = View.GONE
			fragment_my_tickets_upcoming_empty.visibility = View.VISIBLE
		}
	}
}