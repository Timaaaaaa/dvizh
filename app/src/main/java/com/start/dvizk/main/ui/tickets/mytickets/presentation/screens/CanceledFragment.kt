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
import com.start.dvizk.main.ui.tickets.mytickets.data.model.state.CanceledTicketsState
import com.start.dvizk.main.ui.tickets.mytickets.presentation.MyTicketsViewModel
import com.start.dvizk.main.ui.tickets.mytickets.presentation.adapter.CanceledTicketsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CanceledFragment : Fragment() {

	private val viewModel: MyTicketsViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_my_tickets_canceled_recycler: RecyclerView
	private lateinit var fragment_my_tickets_canceled_empty: ConstraintLayout

	private lateinit var fragment_my_tickets_canceled_progressbar: ProgressBar

	private lateinit var canceledTicketsAdapter: CanceledTicketsAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_my_tickets_canceled, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		viewModel.getUserCanceledTickets(1, sharedPreferencesRepository.getUserToken())
	}

	private fun initView(view: View) {
		fragment_my_tickets_canceled_recycler =
			view.findViewById(R.id.fragment_my_tickets_canceled_recycler)
		fragment_my_tickets_canceled_empty =
			view.findViewById(R.id.fragment_my_tickets_canceled_empty)
		fragment_my_tickets_canceled_progressbar =
			view.findViewById(R.id.fragment_my_tickets_canceled_progressbar)
	}

	private fun initObserver() {
		viewModel.userCanceledTicketsStateLiveData.observe(viewLifecycleOwner, ::initList)
	}

	private fun initList(state: CanceledTicketsState) {
		when (state) {
			is CanceledTicketsState.Failed -> {
				fragment_my_tickets_canceled_progressbar.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is CanceledTicketsState.Loading -> {
				fragment_my_tickets_canceled_progressbar.visibility = View.VISIBLE
			}
			is CanceledTicketsState.Success -> {
				val myCanceledTickets = state.canceledTickets

				if (myCanceledTickets.isNotEmpty()) {
					fragment_my_tickets_canceled_empty.visibility = View.GONE
					fragment_my_tickets_canceled_progressbar.visibility = View.GONE

					canceledTicketsAdapter = CanceledTicketsAdapter(resources)
					canceledTicketsAdapter.setData(myCanceledTickets)

					fragment_my_tickets_canceled_recycler.apply {
						visibility = View.VISIBLE
						layoutManager = LinearLayoutManager(
							requireContext(),
							LinearLayoutManager.VERTICAL,
							false
						)
						adapter = canceledTicketsAdapter
					}

				} else {
					fragment_my_tickets_canceled_recycler.visibility = View.GONE
					fragment_my_tickets_canceled_empty.visibility = View.VISIBLE
				}
			}
		}
	}
}