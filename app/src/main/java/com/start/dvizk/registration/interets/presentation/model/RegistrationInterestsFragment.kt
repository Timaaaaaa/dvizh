package com.start.dvizk.registration.interets.presentation.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.CategoryAdapter

class RegistrationInterestsFragment : Fragment() {

	private lateinit var fragment_registration_interests_category_recycler_view: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_registration_interests, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_registration_interests_category_recycler_view = view.findViewById(R.id.fragment_registration_interests_category_recycler_view)
		fragment_registration_interests_category_recycler_view.adapter = CategoryAdapter(resources)
	}
}