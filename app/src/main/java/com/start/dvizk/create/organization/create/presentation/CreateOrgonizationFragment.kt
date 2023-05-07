package com.start.dvizk.create.organization.create.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.start.dvizk.R

class CreateOrgonizationFragment : Fragment() {

	private lateinit var fragment_create_organization_back_image: ImageView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_create_organizations, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
	}

	private fun initView(view: View) {
		fragment_create_organization_back_image = view.findViewById(R.id.fragment_create_organization_back_image)

		fragment_create_organization_back_image.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}
}