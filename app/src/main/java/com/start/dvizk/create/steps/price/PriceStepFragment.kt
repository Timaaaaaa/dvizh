package com.start.dvizk.create.steps.price

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.create.steps.discount.DiscountStepFragment
import com.start.dvizk.create.steps.eventrule.EventRuleStepFragment
import com.start.dvizk.create.steps.language.LanguageStepFragment

class PriceStepFragment : Fragment() {

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var fragment_create_organization_button: Button

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_price_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		fragment_create_organization_button = view.findViewById(R.id.fragment_create_organization_button)

		next.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = EventRuleStepFragment()
			ft.add(R.id.fragment_container,fragment)
			ft.addToBackStack(null)
			ft.commit()
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_create_organization_button.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = DiscountStepFragment()
			ft.add(R.id.fragment_container,fragment)
			ft.addToBackStack(null)
			ft.commit()
		}
	}
}