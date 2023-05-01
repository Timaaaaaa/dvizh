package com.start.dvizk.main.ui.event

import 	android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R

class EventPageFragment : Fragment() {

	private lateinit var fragment_event_page_profile_organizer_avatar: ImageView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_event_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_event_page_profile_organizer_avatar = view.findViewById(R.id.fragment_event_page_profile_organizer_avatar)

		val imageUri = "https://s3.amazonaws.com/bit-photos/large/12849129.jpeg"

		Glide.with(this)
			.asBitmap()
			.load(imageUri)
			.apply(RequestOptions.circleCropTransform())
			.into(fragment_event_page_profile_organizer_avatar)
	}
}