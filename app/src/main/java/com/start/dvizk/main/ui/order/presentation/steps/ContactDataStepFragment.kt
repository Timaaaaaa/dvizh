package com.start.dvizk.main.ui.order.presentation.steps

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.dialog.SuccessDialog
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.order.data.model.TicketOrder
import com.start.dvizk.main.ui.order.presentation.router.OrderTicketScreenRouter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.Calendar

class ContactDataStepFragment : Fragment() {

	private val viewModel: TicketOrderViewModel by sharedViewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_order_contact_details_step_return_button: ImageView

	private lateinit var fragment_order_contact_details_step_user_name_edit_text: EditText
	private lateinit var fragment_order_contact_details_step_user_surname_edit_text: EditText
	private lateinit var fragment_order_contact_details_step_user_birthday_text_view: TextView
	private lateinit var fragment_order_contact_details_step_user_email_edit_text: EditText
	private lateinit var fragment_order_contact_details_step_user_phone_number_edit_text: EditText

	private lateinit var fragment_order_contact_details_step_continue_button: Button

	var name = ""
	var surname = ""
	var birthday = ""
	var email = ""
	var phoneNumber = ""

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_order_contact_data_step, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.ticketOwnerDataRequestStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun initView(view: View) {
		fragment_order_contact_details_step_return_button =
			view.findViewById(R.id.fragment_order_contact_details_step_return_button)
		fragment_order_contact_details_step_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_order_contact_details_step_user_name_edit_text =
			view.findViewById(R.id.fragment_order_contact_details_step_user_name_edit_text)

		fragment_order_contact_details_step_user_surname_edit_text =
			view.findViewById(R.id.fragment_order_contact_details_step_user_surname_edit_text)

		fragment_order_contact_details_step_user_email_edit_text =
			view.findViewById(R.id.fragment_order_contact_details_step_user_email_edit_text)

		fragment_order_contact_details_step_user_phone_number_edit_text =
			view.findViewById(R.id.fragment_order_contact_details_step_user_phone_number_edit_text)

		fragment_order_contact_details_step_user_birthday_text_view =
			view.findViewById(R.id.fragment_order_contact_details_step_user_birthday_text_view)
		fragment_order_contact_details_step_user_birthday_text_view.setOnClickListener {
			val calendar = Calendar.getInstance()
			val year = calendar.get(Calendar.YEAR)
			val month = calendar.get(Calendar.MONTH)
			val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

			val datePickerDialog = DatePickerDialog(
				requireContext(),
				{ view, year, month, dayOfMonth ->
					fragment_order_contact_details_step_user_birthday_text_view.text = "$year-${month + 1}-$dayOfMonth"
					birthday = "$year-${month + 1}-$dayOfMonth"
				}, year, month, dayOfMonth
			)

			datePickerDialog.show()
		}

		fragment_order_contact_details_step_continue_button =
			view.findViewById(R.id.fragment_order_contact_details_step_continue_button)
		fragment_order_contact_details_step_continue_button.setOnClickListener {
			name = fragment_order_contact_details_step_user_name_edit_text.text.toString()
			surname = fragment_order_contact_details_step_user_surname_edit_text.text.toString()
			birthday = fragment_order_contact_details_step_user_birthday_text_view.text.toString()
			email = fragment_order_contact_details_step_user_email_edit_text.text.toString()
			phoneNumber = fragment_order_contact_details_step_user_phone_number_edit_text.text.toString()

			viewModel.sendTicketOwnerData(
				token = sharedPreferencesRepository.getUserToken(),
				ticketOrderId = arguments?.getInt(TICKET_ORDER_ID) ?: 0,
				name = name,
				surname = surname,
				email = email,
				birthday = birthday,
				number = phoneNumber,
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