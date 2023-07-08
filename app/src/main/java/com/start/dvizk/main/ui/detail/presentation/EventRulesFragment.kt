package com.start.dvizk.main.ui.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.start.dvizk.R

class EventRulesFragment : Fragment() {

	private lateinit var fragment_event_rules_return_button: ImageView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_event_rules, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_event_rules_return_button = view.findViewById(R.id.fragment_event_rules_return_button)

		fragment_event_rules_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}
}