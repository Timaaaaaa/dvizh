package com.start.dvizk.main.ui.tickets.ticket.presentation

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.tickets.ticket.data.model.TicketDataModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val TICKET_ID = "ticketId"

class TicketFragment : Fragment() {

	private val viewModel: TicketViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_ticket_return_button: ImageView

	private lateinit var fragment_ticket_progressbar: ProgressBar
	private lateinit var fragment_ticket_layout: ConstraintLayout

	private lateinit var fragment_ticket_image: ImageView
	private lateinit var fragment_ticket_title: TextView
	private lateinit var fragment_ticket_date: TextView
	private lateinit var fragment_ticket_time: TextView
	private lateinit var fragment_ticket_price: TextView
	private lateinit var fragment_ticket_id: TextView
	private lateinit var fragment_ticket_qr: ImageView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_view_ticket, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		viewModel.getTicketById(requireArguments().getInt(TICKET_ID), sharedPreferencesRepository.getUserToken())
	}

	private fun initView(view: View) {
		fragment_ticket_return_button = view.findViewById(R.id.fragment_ticket_return_button)
		fragment_ticket_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_ticket_progressbar = view.findViewById(R.id.fragment_ticket_progressbar)

		fragment_ticket_layout = view.findViewById(R.id.fragment_ticket_layout)

		fragment_ticket_image = view.findViewById(R.id.fragment_ticket_image)
		fragment_ticket_title = view.findViewById(R.id.fragment_ticket_title)
		fragment_ticket_date = view.findViewById(R.id.fragment_ticket_date)
		fragment_ticket_time = view.findViewById(R.id.fragment_ticket_time)
		fragment_ticket_price = view.findViewById(R.id.fragment_ticket_price)
		fragment_ticket_id = view.findViewById(R.id.fragment_ticket_id)
		fragment_ticket_qr = view.findViewById(R.id.fragment_ticket_qr)
	}

	private fun initObserver() {
		viewModel.userTicketStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				fragment_ticket_progressbar.visibility = View.GONE
				fragment_ticket_layout.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {
				fragment_ticket_progressbar.visibility = View.VISIBLE
				fragment_ticket_layout.visibility = View.GONE
			}
			is RequestResponseState.Success -> {
				val ticket = state.value as? TicketDataModel ?: return

				fragment_ticket_date.text = ticket.date
				fragment_ticket_time.text = ticket.time
				fragment_ticket_price.text = ticket.price
				fragment_ticket_id.text = ticket.id.toString()
				fragment_ticket_title.text = ticket.title
				fragment_ticket_progressbar.visibility = View.GONE
				fragment_ticket_layout.visibility = View.VISIBLE



				val textToEncode = "{\"datetime_id\":${ticket.datetime_id},\"ticket_id\":${ticket.id},\"email\":\"${ticket.email}\"}"
				val bitmap = generateQRCode(textToEncode, 500, 500)
				fragment_ticket_qr.setImageBitmap(bitmap)
			}
		}
	}

	private fun generateQRCode(text: String, width: Int, height: Int): Bitmap? {
		val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height)
		val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

		for (x in 0 until width) {
			for (y in 0 until height) {
				bitmap.setPixel(x, y, if (bitMatrix[x, y]) 0xFF000000.toInt() else 0xFFFFFFFF.toInt())
			}
		}

		return bitmap
	}
}