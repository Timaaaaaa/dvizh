package com.start.dvizk.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.start.dvizk.R


class MainPageFragment :
		Fragment(),
		OnClickListener {

	override fun onCreateView(
			inflater: LayoutInflater, container: ViewGroup?,
			savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_main_page, container, false)
	}

	override fun onClick(v: View?) {

	}

}