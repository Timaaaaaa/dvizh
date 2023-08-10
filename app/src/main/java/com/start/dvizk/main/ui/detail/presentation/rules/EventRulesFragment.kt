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
import com.start.dvizk.main.ui.detail.data.model.EventRulesDataModel
import com.start.dvizk.main.ui.detail.presentation.EventDetailViewModel
import com.start.dvizk.main.ui.home.presentation.EVENT_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventRulesFragment : Fragment() {

	private val viewModel: EventDetailViewModel by viewModel()

	private lateinit var fragment_event_rules_return_button: ImageView
	private lateinit var fragment_event_rules_text: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_event_rules, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		val eventId = requireArguments().getInt(EVENT_ID)
		viewModel.getEventRules(eventId)
	}

	private fun initView(view: View) {
		fragment_event_rules_return_button = view.findViewById(R.id.fragment_event_rules_return_button)
		fragment_event_rules_text = view.findViewById(R.id.fragment_event_rules_text)

		fragment_event_rules_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun initObserver() {
		viewModel.eventRulesStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? EventRulesDataModel ?: return

				fragment_event_rules_text.text = response.rules
			}
		}
	}
}