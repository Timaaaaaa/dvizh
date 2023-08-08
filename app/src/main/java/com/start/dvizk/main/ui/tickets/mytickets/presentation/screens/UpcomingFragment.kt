package com.start.dvizk.main.ui.tickets.mytickets.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.main.ui.tickets.mytickets.data.model.MyTicket
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.UpcomingTicketsState
import com.start.dvizk.main.ui.tickets.mytickets.presentation.MyTicketsViewModel
import com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter.UpcomingTicketsAdapter
import com.start.dvizk.main.ui.tickets.ticket.presentation.OnTicketClickListener
import com.start.dvizk.main.ui.tickets.ticket.presentation.TICKET_ID
import com.start.dvizk.main.ui.tickets.ticket.presentation.TicketFragment
import com.start.dvizk.main.ui.tickets.ticket.presentation.cancel.CancelTicketFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpcomingFragment : Fragment(), OnTicketClickListener {

	private val viewModel: MyTicketsViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private var list = mutableListOf<MyTicket>()

	private lateinit var fragment_my_tickets_upcoming_recycler: RecyclerView
	private lateinit var fragment_my_tickets_upcoming_empty: ConstraintLayout

	private lateinit var fragment_my_tickets_upcoming_progressbar: ProgressBar

	private lateinit var upcomingTicketsLayoutManager: LinearLayoutManager
	private lateinit var upcomingTicketsAdapter: UpcomingTicketsAdapter

	private var page = 1
	private var totalPage: Int = 1
	private var loading = true
	private var pastVisibleItems = 0
	private var visibleItemCount = 0
	private var totalItemCount = 0

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets_upcoming, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		viewModel.getUserUpcomingTickets(page, sharedPreferencesRepository.getUserToken())
	}

	override fun onViewTicket(data: MyTicket) {
		val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
		val fragment = TicketFragment()
		fragment.arguments = Bundle().apply {
			putInt(TICKET_ID, data.id)
		}
		ft.add(R.id.nav_host_fragment_activity_main, fragment)
		ft.addToBackStack("")
		ft.commit()
	}

	override fun onCancelTicket(data: MyTicket) {
		val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
		val fragment = CancelTicketFragment()
		fragment.arguments = Bundle().apply {
			putInt(TICKET_ID, data.id)
		}
		ft.add(R.id.nav_host_fragment_activity_main, fragment)
		ft.addToBackStack("")
		ft.commit()
	}

	private fun initView(view: View) {
		fragment_my_tickets_upcoming_recycler =
			view.findViewById(R.id.fragment_my_tickets_upcoming_recycler)
		fragment_my_tickets_upcoming_empty =
			view.findViewById(R.id.fragment_my_tickets_upcoming_empty)
		fragment_my_tickets_upcoming_progressbar =
			view.findViewById(R.id.fragment_my_tickets_upcoming_progressbar)
	}

	private fun initObserver() {
		viewModel.userUpcomingTicketsStateLiveData.observe(viewLifecycleOwner, ::initList)
	}

	private fun initList(state: UpcomingTicketsState) {
		when (state) {
			is UpcomingTicketsState.Failed -> {
				fragment_my_tickets_upcoming_progressbar.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is UpcomingTicketsState.Loading -> {
				fragment_my_tickets_upcoming_progressbar.visibility = View.VISIBLE
			}
			is UpcomingTicketsState.Success -> {
				totalPage = state.totalPage
				setupPagination(totalPage)
				val myUpcomingTickets = state.upcomingTickets
				list.addAll(myUpcomingTickets)

				if (myUpcomingTickets.isNotEmpty()) {
					fragment_my_tickets_upcoming_empty.visibility = View.GONE
					fragment_my_tickets_upcoming_progressbar.visibility = View.GONE

					upcomingTicketsLayoutManager = LinearLayoutManager(
						requireContext(),
						LinearLayoutManager.VERTICAL,
						false
					)

					upcomingTicketsAdapter = UpcomingTicketsAdapter(resources)
					upcomingTicketsAdapter.setListener(this)
					upcomingTicketsAdapter.setData(list)

					fragment_my_tickets_upcoming_recycler.apply {
						visibility = View.VISIBLE
						layoutManager = upcomingTicketsLayoutManager
						adapter = upcomingTicketsAdapter
					}

				} else {
					fragment_my_tickets_upcoming_recycler.visibility = View.GONE
					fragment_my_tickets_upcoming_empty.visibility = View.VISIBLE
				}

				loading = true
			}
		}
	}

	// пока не работает
	private fun setupPagination(totalPage: Int) {
		fragment_my_tickets_upcoming_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				if (dy > 0) {
					visibleItemCount = upcomingTicketsLayoutManager.childCount
					totalItemCount = upcomingTicketsLayoutManager.itemCount
					pastVisibleItems = upcomingTicketsLayoutManager.findFirstVisibleItemPosition()
					if (loading) {
						if (visibleItemCount + pastVisibleItems >= totalItemCount) {
							loading = false
							if (page != totalPage) {
								viewModel.getUserUpcomingTickets(++page, sharedPreferencesRepository.getUserToken())
							}
						}
					}
				}
			}
		})
	}
}