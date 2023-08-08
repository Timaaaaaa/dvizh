package com.start.dvizk.main.ui.tickets.ticket.presentation.cancel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.dialog.OnSuccessDialogOk
import com.start.dvizk.create.dialog.SUCCESS_DIALOG_SUBTITLE
import com.start.dvizk.create.dialog.SUCCESS_DIALOG_TITLE
import com.start.dvizk.create.dialog.SuccessDialog
import com.start.dvizk.main.ui.tickets.ticket.data.model.TicketCancelingState
import com.start.dvizk.main.ui.tickets.ticket.presentation.TICKET_ID
import com.start.dvizk.main.ui.tickets.ticket.presentation.TicketViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class CancelTicketFragment : Fragment(), OnSuccessDialogOk {

	private val viewModel: TicketViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private var cancelReason: String = ""

	private lateinit var fragment_cancel_ticket_radio_group: RadioGroup

	private lateinit var fragment_cancel_ticket_radio_text_1: TextView
	private lateinit var fragment_cancel_ticket_radio_text_2: TextView
	private lateinit var fragment_cancel_ticket_radio_text_3: TextView
	private lateinit var fragment_cancel_ticket_radio_text_4: TextView
	private lateinit var fragment_cancel_ticket_radio_text_5: TextView
	private lateinit var fragment_cancel_ticket_radio_text_6: TextView
	private lateinit var fragment_cancel_ticket_radio_text_7: TextView

	private lateinit var fragment_cancel_ticket_other_edit_text: EditText

	private lateinit var fragment_cancel_ticket_cancel_button: Button

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_cancel_ticket, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()
	}

	private fun initView(view: View) {
		fragment_cancel_ticket_radio_group =
			view.findViewById(R.id.fragment_cancel_ticket_radio_group)

		fragment_cancel_ticket_radio_text_1 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_1)
		fragment_cancel_ticket_radio_text_2 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_2)
		fragment_cancel_ticket_radio_text_3 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_3)
		fragment_cancel_ticket_radio_text_4 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_4)
		fragment_cancel_ticket_radio_text_5 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_5)
		fragment_cancel_ticket_radio_text_6 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_6)
		fragment_cancel_ticket_radio_text_7 =
			view.findViewById(R.id.fragment_cancel_ticket_radio_text_7)

		fragment_cancel_ticket_other_edit_text =
			view.findViewById(R.id.fragment_cancel_ticket_other_edit_text)

		fragment_cancel_ticket_radio_group.setOnCheckedChangeListener { _, checkedId ->
			when (checkedId) {
				R.id.fragment_cancel_ticket_radio_button_1 -> {
					cancelReason = fragment_cancel_ticket_radio_text_1.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_2 -> {
					cancelReason = fragment_cancel_ticket_radio_text_2.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_3 -> {
					cancelReason = fragment_cancel_ticket_radio_text_3.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_4 -> {
					cancelReason = fragment_cancel_ticket_radio_text_4.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_5 -> {
					cancelReason = fragment_cancel_ticket_radio_text_5.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_6 -> {
					cancelReason = fragment_cancel_ticket_radio_text_6.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
				R.id.fragment_cancel_ticket_radio_button_7 -> {
					cancelReason = fragment_cancel_ticket_radio_text_7.text.toString()
					fragment_cancel_ticket_other_edit_text.setText("")
				}
			}
		}

		fragment_cancel_ticket_other_edit_text.addTextChangedListener(object: TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
			override fun afterTextChanged(s: Editable?) {
				fragment_cancel_ticket_radio_group.clearCheck()
				cancelReason = fragment_cancel_ticket_other_edit_text.text.toString()
			}
		})

		fragment_cancel_ticket_cancel_button =
			view.findViewById(R.id.fragment_cancel_ticket_cancel_button)
		fragment_cancel_ticket_cancel_button.setOnClickListener {
			viewModel.cancelTicket(requireArguments().getInt(TICKET_ID), sharedPreferencesRepository.getUserToken())
		}
	}

	private fun initObserver() {
		viewModel.userTicketCancelingState.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: TicketCancelingState) {
		when (state) {
			is TicketCancelingState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is TicketCancelingState.Loading -> {

			}
			is TicketCancelingState.Success -> {
				if (state.message == "Билет успешно отменен") {
					val dialog = SuccessDialog()
					dialog.setListener(this)
					dialog.arguments = Bundle().apply {
						putString(SUCCESS_DIALOG_TITLE, "Отменено")
						putString(SUCCESS_DIALOG_SUBTITLE, "Вы успешно отменили мероприятие. 80% средств будут возвращены на ваш счет.")
					}
					dialog.show(requireActivity().supportFragmentManager)
					Toast.makeText(requireContext(), "Причина отмены: $cancelReason", Toast.LENGTH_LONG).show()
				}
			}
		}
	}

	override fun onPositiveClickButton() {
		requireActivity().supportFragmentManager.popBackStack()
	}
}