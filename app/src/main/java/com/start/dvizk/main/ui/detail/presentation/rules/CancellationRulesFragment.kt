package com.start.dvizk.main.ui.detail.presentation.rules

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.start.dvizk.R
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.detail.data.model.CancellationRulesDataModel
import com.start.dvizk.main.ui.detail.presentation.EventDetailViewModel
import com.start.dvizk.main.ui.home.presentation.EVENT_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class CancellationRulesFragment : Fragment() {

	private val viewModel: EventDetailViewModel by viewModel()

	private lateinit var fragment_cancel_rules_return_button: ImageView
	private lateinit var fragment_cancel_rules_text: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_cancellation_rules, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initView(view)
		initObserver()

		val eventId = requireArguments().getInt(EVENT_ID)
		viewModel.getCancellationRules(eventId)
	}

	private fun initView(view: View) {
		fragment_cancel_rules_return_button = view.findViewById(R.id.fragment_cancel_rules_return_button)
		fragment_cancel_rules_text = view.findViewById(R.id.fragment_cancel_rules_text)

		fragment_cancel_rules_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun initObserver() {
		viewModel.cancellationRulesStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? CancellationRulesDataModel ?: return

				fragment_cancel_rules_text.text = response.cancellation_rules
			}
		}
	}
}