package com.start.dvizk.main.ui.event

import    android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.start.dvizk.R

class DetailPageFragment : Fragment() {

	private lateinit var fragment_event_page_profile_organizer_avatar: ImageView
	private lateinit var fragment_event_page_detail_infos: RecyclerView
	private lateinit var fragment_event_page_items_checklist: RecyclerView
	private lateinit var fragment_event_page_carousel: ImageSlider
	private lateinit var backImageView: ImageView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_detail_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Carousel
		fragment_event_page_carousel = view.findViewById(R.id.fragment_event_page_carousel)

		backImageView = view.findViewById<ImageView>(R.id.fragment_event_page_return_button)
		backImageView.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		val images = ArrayList<SlideModel>();

		images.add(SlideModel(R.drawable.temp_event_1))
		images.add(SlideModel(R.drawable.temp_event_2))
		images.add(SlideModel(R.drawable.temp_event_3))

		fragment_event_page_carousel.setImageList(images, ScaleTypes.CENTER_CROP)

		// Recyclers
		fragment_event_page_detail_infos = view.findViewById(R.id.fragment_event_page_detail_infos)
		fragment_event_page_detail_infos.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
		fragment_event_page_items_checklist =
			view.findViewById(R.id.fragment_event_page_items_checklist)
		fragment_event_page_items_checklist.layoutManager = LinearLayoutManager(view.context)

		var detailsInfos = mutableListOf<DetailsInfo>(
			DetailsInfo("Алматы, Дворец Республики", "Декабрь 24, 2022", "20:00 - 23:00", "Казахский, Русский", "5000 ₸ - 10.000 ₸"),
			DetailsInfo("Актобе", "Декабрь 24, 2022", "20:00 - 23:00", "Казахский, Русский", "5000 ₸ - 10.000 ₸")
		)

		val data2 = ArrayList<CheckList>()

		val clTitles = arrayOf(
			"Перекус для пикника",
			"Воду и чай в термосе",
			"Солнцезащитные очки",
			"Головной убор",
			"СПФ, СПФ и еще раз СПФ!"
		)

		for (i in 1..5) {
			data2.add(
				CheckList(
					R.drawable.ic_check,
					clTitles[i - 1]
				)
			)
		}

		val rwAdapter = DetailsInfoAdapter(detailsInfos)
		fragment_event_page_detail_infos.adapter = rwAdapter
		val clAdapter = CheckListAdapter(data2)
		fragment_event_page_items_checklist.adapter = clAdapter

		fragment_event_page_profile_organizer_avatar =
			view.findViewById(R.id.fragment_event_page_profile_organizer_avatar)

		// Glide
		val imageUri = "https://s3.amazonaws.com/bit-photos/large/12849129.jpeg"

		Glide.with(this)
			.asBitmap()
			.load(imageUri)
			.apply(RequestOptions.circleCropTransform())
			.into(fragment_event_page_profile_organizer_avatar)
	}
}