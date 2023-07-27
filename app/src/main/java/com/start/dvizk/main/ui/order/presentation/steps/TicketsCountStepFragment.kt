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
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R

class TicketsCountStepFragment : Fragment() {

	private var count: Int = 1

	private lateinit var fragment_order_tickets_count_step_return_button: ImageView

	private lateinit var fragment_order_tickets_count_step_remove_button: Button
	private lateinit var fragment_order_tickets_count_step_count: TextView
	private lateinit var fragment_order_tickets_count_step_add_button: Button

	private lateinit var fragment_order_tickets_count_step_continue_button: Button

	private lateinit var orderCheckBox: CheckBox

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

		orderCheckBox = view.findViewById(R.id.order_checkbox)

		fragment_order_tickets_count_step_continue_button =
			view.findViewById(R.id.fragment_order_tickets_count_step_continue_button)
		fragment_order_tickets_count_step_continue_button.setOnClickListener {
			if (orderCheckBox.isChecked) {
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				ft.add(R.id.nav_host_fragment_activity_main, CommandDataStepFragment())
				ft.addToBackStack("")
				ft.commit()
			} else {
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				ft.add(R.id.nav_host_fragment_activity_main, ContactDataStepFragment())
				ft.addToBackStack("")
				ft.commit()
			}
		}
	}
}