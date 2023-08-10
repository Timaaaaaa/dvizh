package com.start.dvizk.main.ui.order.presentation.steps

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.dialog.SuccessDialog
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.order.data.model.TeamData
import com.start.dvizk.main.ui.order.data.model.TeamMember
import com.start.dvizk.main.ui.order.data.model.TicketOrder
import com.start.dvizk.main.ui.order.presentation.adapter.CommandDataAdapter
import com.start.dvizk.main.ui.order.presentation.router.OrderTicketScreenRouter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CommandDataStepFragment : Fragment() {

	private val viewModel: TicketOrderViewModel by sharedViewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private var commandName = ""
	private var commandMembers = ""

	private var maxCommandSize = 5

	private lateinit var fragment_order_command_data_step_return_button: ImageView
	private lateinit var fragment_order_command_data_plus_icon: ImageView
	private lateinit var fragment_order_command_data_step_continue_button: Button

	private lateinit var fragment_order_command_data_step_command_name_edit_text: EditText

	private lateinit var fragment_order_command_data_step_recycler_view: RecyclerView
	private lateinit var commandLayoutManager: LinearLayoutManager

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_order_command_data_step, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.ticketTeamDataRequestStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	private fun initView(view: View) {

		fragment_order_command_data_step_return_button =
			view.findViewById(R.id.fragment_order_command_data_step_return_button)
		fragment_order_command_data_step_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_order_command_data_step_command_name_edit_text =
			view.findViewById(R.id.fragment_order_command_data_step_command_name_edit_text)
		fragment_order_command_data_step_command_name_edit_text.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
			override fun afterTextChanged(s: Editable?) {
				commandName = fragment_order_command_data_step_command_name_edit_text.text.toString()
			}
		})

		fragment_order_command_data_step_recycler_view =
			view.findViewById(R.id.fragment_order_command_data_step_recycler_view)
		commandLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		fragment_order_command_data_step_recycler_view.layoutManager = commandLayoutManager


		val commandDataAdapter = CommandDataAdapter()

		fragment_order_command_data_plus_icon =
			view.findViewById(R.id.fragment_order_command_data_plus_icon)
		fragment_order_command_data_plus_icon.setOnClickListener {
			if (commandDataAdapter.itemCount < maxCommandSize) {
				commandDataAdapter.addItem()
			} else {
				Toast.makeText(requireContext(), "Максимальное количество участников", Toast.LENGTH_SHORT).show()
			}
		}

		fragment_order_command_data_step_continue_button =
			view.findViewById(R.id.fragment_order_command_data_step_continue_button)
		fragment_order_command_data_step_continue_button.setOnClickListener {
			val commandMembersList = commandDataAdapter.getData()
			var list = commandMembersList.map {
				TeamMember(it)
			}
			Snackbar.make(view, "$commandName: $commandMembers", Snackbar.LENGTH_SHORT).show()

			viewModel.setTeamData(
				token = sharedPreferencesRepository.getUserToken(),
				orderId = arguments?.getInt(TICKET_ORDER_ID) ?: 0,
				teamData = TeamData(
					commandName,
					list
				)
			)
		}

		fragment_order_command_data_step_recycler_view.adapter = commandDataAdapter
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

				if(response.screen == "success") {
					val dialog = SuccessDialog()
					dialog.show(requireActivity().supportFragmentManager, "GenderSelectionDialog")

					return
				}

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