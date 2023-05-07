package com.start.dvizk.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.create.organization.list.presentation.OrgаnizationsListFragment

class InstructionFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_instruction, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		val image = view.findViewById<ImageView>(R.id.bottom_image)

		image.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val homeFragment = OrgаnizationsListFragment()
			ft.add(R.id.fragment_container,homeFragment)
			ft.addToBackStack(null)
			ft.commit()
		}
	}
}