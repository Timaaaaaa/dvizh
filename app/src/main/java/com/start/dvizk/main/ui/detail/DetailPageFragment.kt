package com.start.dvizk.main.ui.detail

import    android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.CheckListDataModal
import com.start.dvizk.main.ui.detail.data.DetailsInfoDataModal
import com.start.dvizk.main.ui.detail.presentation.CancelRulesFragment
import com.start.dvizk.main.ui.detail.presentation.CheckListAdapter
import com.start.dvizk.main.ui.detail.presentation.DetailsInfoAdapter
import com.start.dvizk.main.ui.detail.presentation.EventRulesFragment
import com.start.dvizk.main.ui.notifications.NotificationsFragment

class DetailPageFragment : Fragment() {

	private lateinit var fragment_event_page_profile_organizer_avatar: ImageView
	private lateinit var fragment_event_page_detail_infos: RecyclerView
	private lateinit var fragment_event_page_items_checklist: RecyclerView
	private lateinit var fragment_event_page_carousel: ImageSlider
	private lateinit var backImageView: ImageView

	private lateinit var fragment_detail_page_rules_of_event_button: Button
	private lateinit var fragment_detail_page_cancellation_rules_button: Button

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
		fragment_event_page_carousel = view.findViewById(R.id.fragment_detail_page_carousel)

		backImageView = view.findViewById<ImageView>(R.id.fragment_detail_page_return_button)
		backImageView.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		val images = ArrayList<SlideModel>();

		images.add(SlideModel(R.drawable.temp_event_1))
		images.add(SlideModel(R.drawable.temp_event_2))
		images.add(SlideModel(R.drawable.temp_event_3))

		fragment_event_page_carousel.setImageList(images, ScaleTypes.CENTER_CROP)

		// Recyclers
		fragment_event_page_detail_infos = view.findViewById(R.id.fragment_detail_page_detail_infos)
		fragment_event_page_detail_infos.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
		fragment_event_page_items_checklist =
			view.findViewById(R.id.fragment_detail_page_items_checklist)
		fragment_event_page_items_checklist.layoutManager = LinearLayoutManager(view.context)

		var detailsInformation = mutableListOf<DetailsInfoDataModal>(
			DetailsInfoDataModal("Алматы, Дворец Республики", "Декабрь 24, 2022", "20:00 - 23:00", "Казахский, Русский", "5000 ₸ - 10.000 ₸"),
			DetailsInfoDataModal("Актобе", "Декабрь 24, 2022", "20:00 - 23:00", "Казахский, Русский", "5000 ₸ - 10.000 ₸")
		)

		var checkList = mutableListOf<CheckListDataModal>(
			CheckListDataModal(R.drawable.ic_check, "Перекус для пикника"),
			CheckListDataModal(R.drawable.ic_check, "Воду и чай в термосе"),
			CheckListDataModal(R.drawable.ic_check, "Солнцезащитные очки"),
			CheckListDataModal(R.drawable.ic_check, "Головной убор"),
			CheckListDataModal(R.drawable.ic_check, "СПФ, СПФ и еще раз СПФ!")
		)

		val detailsInfoAdapter = DetailsInfoAdapter(detailsInformation)
		fragment_event_page_detail_infos.adapter = detailsInfoAdapter
		val checkListAdapter = CheckListAdapter(checkList)
		fragment_event_page_items_checklist.adapter = checkListAdapter

		fragment_event_page_profile_organizer_avatar =
			view.findViewById(R.id.fragment_detail_page_profile_organizer_avatar)

		// Glide
		val imageUri = "https://s3.amazonaws.com/bit-photos/large/12849129.jpeg"

		Glide.with(this)
			.asBitmap()
			.load(imageUri)
			.apply(RequestOptions.circleCropTransform())
			.into(fragment_event_page_profile_organizer_avatar)

		fragment_detail_page_rules_of_event_button = view.findViewById(R.id.fragment_detail_page_rules_of_event_button)
		fragment_detail_page_rules_of_event_button.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

			ft.add(R.id.nav_host_fragment_activity_main, EventRulesFragment())
			ft.addToBackStack(null)
			ft.commit()
		}

		fragment_detail_page_cancellation_rules_button = view.findViewById(R.id.fragment_detail_page_cancellation_rules_button)
		fragment_detail_page_cancellation_rules_button.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

			ft.add(R.id.nav_host_fragment_activity_main, CancelRulesFragment())
			ft.addToBackStack(null)
			ft.commit()
		}
	}
}