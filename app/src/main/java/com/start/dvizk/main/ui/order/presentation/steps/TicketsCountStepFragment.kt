package com.start.dvizk.main.ui.order.presentation.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.main.ui.detail.presentation.ORDER_DATE_TIME_ID
import com.start.dvizk.main.ui.order.data.model.TicketOrder
import com.start.dvizk.main.ui.order.presentation.router.OrderTicketScreenRouter
import com.start.dvizk.search.search.presentation.SearchViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TICKET_ORDER_ID = "TICKET_ORDER_ID"
const val TICKET_ORDER_SECONDS_LEFT = "TICKET_ORDER_SECONDS_LEFT"

class TicketsCountStepFragment : Fragment() {

	private val viewModel: TicketOrderViewModel by sharedViewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private var count: Int = 1

	private lateinit var fragment_order_tickets_count_step_return_button: ImageView

	private lateinit var fragment_order_tickets_count_step_remove_button: Button
	private lateinit var fragment_order_tickets_count_step_count: TextView
	private lateinit var fragment_order_tickets_count_step_add_button: Button

	private lateinit var fragment_order_tickets_count_step_continue_button: Button

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_order_tickets_count_step, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.ticketCountRequestStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun initView(view: View) {
		fragment_order_tickets_count_step_return_button =
			view.findViewById(R.id.fragment_order_tickets_count_step_return_button)
		fragment_order_tickets_count_step_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_order_tickets_count_step_remove_button =
			view.findViewById(R.id.fragment_order_tickets_count_step_remove_button)
		fragment_order_tickets_count_step_count =
			view.findViewById(R.id.fragment_order_tickets_count_step_count)
		fragment_order_tickets_count_step_add_button =
			view.findViewById(R.id.fragment_order_tickets_count_step_add_button)

		fragment_order_tickets_count_step_remove_button.setOnClickListener{
			if (count != 1) {
				count--
				fragment_order_tickets_count_step_count.text = count.toString()
			}
		}

		fragment_order_tickets_count_step_add_button.setOnClickListener {
			count++
			fragment_order_tickets_count_step_count.text = count.toString()
		}


		fragment_order_tickets_count_step_continue_button =
			view.findViewById(R.id.fragment_order_tickets_count_step_continue_button)
		fragment_order_tickets_count_step_continue_button.setOnClickListener {
			val dateTimeId = arguments?.getInt(ORDER_DATE_TIME_ID) ?: 0
			viewModel.sendTicketCount(
				token = sharedPreferencesRepository.getUserToken(),
				ticketsAmount = count,
				datetimeId = dateTimeId
			)
		}
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? TicketOrder ?: return responseFailed()

				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

				val fragment = OrderTicketScreenRouter.getTicketOrderStepFragment(response.screen)
				fragment.arguments = Bundle().apply {
					putInt(TICKET_ORDER_ID, response.order.id)
					putInt(TICKET_ORDER_SECONDS_LEFT, response.order.seconds_left)
				}
				ft.add(R.id.nav_host_fragment_activity_main, fragment)
				ft.addToBackStack("")
				ft.commit()
			}
		}
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG).show()
	}
}