package com.start.dvizk.main.ui.tickets.mytickets.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.FinishedTicketsState
import com.start.dvizk.main.ui.tickets.mytickets.presentation.MyTicketsViewModel
import com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter.FinishedTicketsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FinishedFragment : Fragment() {

	private val viewModel: MyTicketsViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_my_tickets_finished_recycler: RecyclerView
	private lateinit var fragment_my_tickets_finished_empty: ConstraintLayout

	private lateinit var fragment_my_tickets_finished_progressbar: ProgressBar

	private lateinit var finishedTicketsAdapter: FinishedTicketsAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_my_tickets_finished, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		viewModel.getUserFinishedTickets(1, sharedPreferencesRepository.getUserToken())
	}

	private fun initView(view: View) {
		fragment_my_tickets_finished_recycler =
			view.findViewById(R.id.fragment_my_tickets_finished_recycler)
		fragment_my_tickets_finished_empty =
			view.findViewById(R.id.fragment_my_tickets_finished_empty)
		fragment_my_tickets_finished_progressbar =
			view.findViewById(R.id.fragment_my_tickets_finished_progressbar)
	}

	private fun initObserver() {
		viewModel.userFinishedTicketsStateLiveData.observe(viewLifecycleOwner, ::initList)
	}

	private fun initList(state: FinishedTicketsState) {
		when (state) {
			is FinishedTicketsState.Failed -> {
				fragment_my_tickets_finished_progressbar.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is FinishedTicketsState.Loading -> {
				fragment_my_tickets_finished_progressbar.visibility = View.VISIBLE
			}
			is FinishedTicketsState.Success -> {
				val myFinishedTickets = state.finishedTickets

				if (myFinishedTickets.isNotEmpty()) {
					fragment_my_tickets_finished_empty.visibility = View.GONE
					fragment_my_tickets_finished_progressbar.visibility = View.GONE

					finishedTicketsAdapter = FinishedTicketsAdapter(resources)
					finishedTicketsAdapter.setData(myFinishedTickets)

					fragment_my_tickets_finished_recycler.apply {
						visibility = View.VISIBLE
						layoutManager = LinearLayoutManager(
							requireContext(),
							LinearLayoutManager.VERTICAL,
							false
						)
						adapter = finishedTicketsAdapter
					}

				} else {
					fragment_my_tickets_finished_recycler.visibility = View.GONE
					fragment_my_tickets_finished_empty.visibility = View.VISIBLE
				}
			}
		}
	}
}