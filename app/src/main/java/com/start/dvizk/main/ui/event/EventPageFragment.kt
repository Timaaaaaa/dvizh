package com.start.dvizk.main.ui.event

import 	android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R

class EventPageFragment : Fragment() {

	private lateinit var fragment_event_page_profile_organizer_avatar: ImageView
	private lateinit var fragment_event_page_recycler_view: RecyclerView
	private lateinit var fragment_event_page_items_checklist: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_event_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		fragment_event_page_recycler_view = view.findViewById(R.id.fragment_event_page_recycler_view)
		fragment_event_page_recycler_view.layoutManager = LinearLayoutManager(view.context)
		fragment_event_page_items_checklist = view.findViewById(R.id.fragment_event_page_items_checklist)
		fragment_event_page_items_checklist.layoutManager = LinearLayoutManager(view.context)

		val data1 = ArrayList<DetailsInfo>()

		val rwIcons = arrayOf(R.drawable.ic_calendar, R.drawable.ic_message, R.drawable.ic_ticket, R.drawable.ic_geo)
		val rwTitles = arrayOf("Декабрь 24, 2022", "Язык", "$20.00 - $100.00", "Алматы, Казахстан")
		val rwSubtitles1 = arrayOf("Начало: 20:00", "Казахский, Русский", "Цена билета зависит от пакета", "Тимирязева 60")
		val rwSubtitles2 = arrayOf("Длительность: 3 часа", "", "", "")

		for (i in 1..4) {
			data1.add(
				DetailsInfo(
				rwIcons[i-1],
				rwTitles[i-1],
				rwSubtitles1[i-1],
				rwSubtitles2[i-1]
			)
			)
		}

		val data2 = ArrayList<CheckList>()

		val clTitles = arrayOf("Перекус для пикника", "Воду и чай в термосе", "Солнцезащитные очки", "Головной убор", "СПФ, СПФ и еще раз СПФ!")

		for (i in 1..5) {
			data2.add(
				CheckList(
				R.drawable.ic_check,
				clTitles[i-1]
			)
			)
		}

		val rwAdapter = DetailsInfoAdapter(data1)
		fragment_event_page_recycler_view.adapter = rwAdapter
		val clAdapter = CheckListAdapter(data2)
		fragment_event_page_items_checklist.adapter = clAdapter

		fragment_event_page_profile_organizer_avatar = view.findViewById(R.id.fragment_event_page_profile_organizer_avatar)

		val imageUri = "https://s3.amazonaws.com/bit-photos/large/12849129.jpeg"

		Glide.with(this)
			.asBitmap()
			.load(imageUri)
			.apply(RequestOptions.circleCropTransform())
			.into(fragment_event_page_profile_organizer_avatar)
	}
}