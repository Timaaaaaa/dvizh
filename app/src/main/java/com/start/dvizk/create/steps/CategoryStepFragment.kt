package com.start.dvizk.create.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.create.steps.bottomsheet.BottomSheetSelectListFragment

class CategoryStepFragment : Fragment() {

	private lateinit var categoriesButton: Button
	private lateinit var next: Button
	private lateinit var back: Button

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_category_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		categoriesButton = view.findViewById(R.id.fragment_create_organization_button)
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)

		next.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = AboutStepFragment()
			ft.add(R.id.fragment_container,fragment)
			ft.addToBackStack(null)
			ft.commit()
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		categoriesButton.setOnClickListener {
			val bottomSheetFragment = BottomSheetSelectListFragment()
			bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
		}
	}
}