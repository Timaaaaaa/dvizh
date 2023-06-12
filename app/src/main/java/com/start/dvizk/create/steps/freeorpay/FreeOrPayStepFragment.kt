package com.start.dvizk.create.steps.freeorpay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.create.steps.language.LanguageStepFragment
import com.start.dvizk.create.steps.price.PriceStepFragment

class FreeOrPayStepFragment : Fragment() {

	private lateinit var next: Button
	private lateinit var back: Button

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_free_or_pay_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)

		next.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = PriceStepFragment()
			ft.add(R.id.fragment_container,fragment)
			ft.addToBackStack(null)
			ft.commit()
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}
}