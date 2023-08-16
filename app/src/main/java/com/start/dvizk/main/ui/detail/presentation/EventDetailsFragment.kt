package com.start.dvizk.main.ui.detail.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.snackbar.Snackbar
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.detail.data.model.CheckListDataModel
import com.start.dvizk.main.ui.detail.data.model.DateTime
import com.start.dvizk.main.ui.detail.data.model.DetailsInfoDataModel
import com.start.dvizk.main.ui.detail.data.model.EventDetailDataModel
import com.start.dvizk.main.ui.detail.presentation.adapter.CheckListAdapter
import com.start.dvizk.main.ui.detail.presentation.adapter.DateTimesAdapter
import com.start.dvizk.main.ui.detail.presentation.adapter.DetailsInfoAdapter
import com.start.dvizk.main.ui.detail.presentation.rules.CancellationRulesFragment
import com.start.dvizk.main.ui.detail.presentation.rules.EventRulesFragment
import com.start.dvizk.main.ui.home.presentation.EVENT_ID
import com.start.dvizk.main.ui.order.presentation.router.OrderTicketScreenRouter
import com.start.dvizk.main.ui.order.presentation.steps.TicketsCountStepFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val ORDER_DATE_TIME_ID = "ORDER_DATETIME_ID"

class EventDetailsFragment : Fragment(), OnDateTimeClickListener {

	private val viewModel: EventDetailViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_detail_page_return_button: ImageView

	private lateinit var fragment_detail_page_carousel: ImageSlider
	private val images = mutableListOf<SlideModel>()
	private lateinit var fragment_detail_page_header_title: TextView
	private lateinit var fragment_detail_page_location_address: TextView
	private lateinit var fragment_detail_page_who_can_participate_requirements: TextView
	private lateinit var fragment_detail_page_program_of_event_activities: TextView
	private lateinit var fragment_detail_page_organizer_services_info: TextView

	private lateinit var fragment_detail_page_organizer_avatar: ImageView
	private lateinit var fragment_detail_page_organizer_name: TextView
	private lateinit var fragment_detail_page_organizer_description: TextView

	private lateinit var fragment_detail_page_contacts_phone_number: ImageView
	private lateinit var fragment_detail_page_contacts_whatsapp: ImageView
	private lateinit var fragment_detail_page_contacts_instagram: ImageView
	private lateinit var fragment_detail_page_contacts_google: ImageView

	private lateinit var fragment_detail_page_rules_of_event_button: Button
	private lateinit var fragment_detail_page_cancellation_rules_button: Button

	private lateinit var fragment_detail_page_date_times_recycler: RecyclerView
	private lateinit var dateTimesAdapter: DateTimesAdapter
	private val dateTimes = mutableListOf<DateTime>()
	private var dateTimeId: Int = 0


	private lateinit var fragment_detail_page_detail_info: RecyclerView
	private lateinit var detailsInfoAdapter: DetailsInfoAdapter
	private val detailsInformation = mutableListOf<DetailsInfoDataModel>()

	private lateinit var fragment_detail_page_items_checklist: RecyclerView
	private lateinit var checkListAdapter: CheckListAdapter
	private val checkList = mutableListOf<CheckListDataModel>()

	private lateinit var fragment_detail_page_book_event_button: Button
	private var eventDetailDataModel: EventDetailDataModel? = null

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_detail_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initLists()
		initObserver()

		viewModel.getEventDetails(requireArguments().getInt(EVENT_ID))
	}

	override fun onItemClick(dateTime: DateTime) {
		dateTimeId = dateTime.id
	}

	private fun initView(view: View) {

		fragment_detail_page_return_button =
			view.findViewById(R.id.fragment_detail_page_return_button)
		fragment_detail_page_return_button.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		// Carousel
		fragment_detail_page_carousel =
			view.findViewById(R.id.fragment_detail_page_carousel)

		fragment_detail_page_header_title =
			view.findViewById(R.id.fragment_detail_page_header_title)

		fragment_detail_page_date_times_recycler =
			view.findViewById(R.id.fragment_detail_page_date_times_recycler)

		fragment_detail_page_detail_info =
			view.findViewById(R.id.fragment_detail_page_detail_info)

		fragment_detail_page_location_address =
			view.findViewById(R.id.fragment_detail_page_location_address)

		fragment_detail_page_who_can_participate_requirements =
			view.findViewById(R.id.fragment_detail_page_who_can_participate_requirements)

		fragment_detail_page_program_of_event_activities =
			view.findViewById(R.id.fragment_detail_page_program_of_event_activities)

		fragment_detail_page_organizer_services_info =
			view.findViewById(R.id.fragment_detail_page_organizer_services_info)

		fragment_detail_page_items_checklist =
			view.findViewById(R.id.fragment_detail_page_items_checklist)

		fragment_detail_page_organizer_avatar =
			view.findViewById(R.id.fragment_detail_page_organizer_avatar)
		fragment_detail_page_organizer_name =
			view.findViewById(R.id.fragment_detail_page_organizer_name)
		fragment_detail_page_organizer_description =
			view.findViewById(R.id.fragment_detail_page_organizer_description)

		fragment_detail_page_contacts_phone_number =
			view.findViewById(R.id.fragment_detail_page_contacts_phone_number)
		fragment_detail_page_contacts_whatsapp =
			view.findViewById(R.id.fragment_detail_page_contacts_whatsapp)
		fragment_detail_page_contacts_instagram =
			view.findViewById(R.id.fragment_detail_page_contacts_instagram)
		fragment_detail_page_contacts_google =
			view.findViewById(R.id.fragment_detail_page_contacts_google)

		fragment_detail_page_rules_of_event_button =
			view.findViewById(R.id.fragment_detail_page_rules_of_event_button)
		fragment_detail_page_rules_of_event_button.setOnClickListener {
			val eventID = requireArguments().getInt(EVENT_ID)
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = EventRulesFragment()
			fragment.arguments = Bundle().apply {
				putInt(EVENT_ID, eventID)
			}
			ft.add(R.id.nav_host_fragment_activity_main, fragment)
			ft.addToBackStack("")
			ft.commit()
		}

		fragment_detail_page_cancellation_rules_button =
			view.findViewById(R.id.fragment_detail_page_cancellation_rules_button)
		fragment_detail_page_cancellation_rules_button.setOnClickListener {
			val eventID = requireArguments().getInt(EVENT_ID)
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val fragment = CancellationRulesFragment()
			fragment.arguments = Bundle().apply {
				putInt(EVENT_ID, eventID)
			}
			ft.add(R.id.nav_host_fragment_activity_main, fragment)
			ft.addToBackStack("")
			ft.commit()
		}

		fragment_detail_page_book_event_button =
			view.findViewById(R.id.fragment_detail_page_book_event_button)
		fragment_detail_page_book_event_button.setOnClickListener {
			viewModel.orderFirstStep(sharedPreferencesRepository.getUserToken(), dateTimeId)
		}
	}

	private fun initLists() {
		dateTimesAdapter = DateTimesAdapter(resources)
		dateTimesAdapter.setListener(this)

		fragment_detail_page_date_times_recycler.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
			adapter = dateTimesAdapter
		}

		checkListAdapter = CheckListAdapter()
		fragment_detail_page_items_checklist.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			adapter = checkListAdapter
		}

		detailsInfoAdapter = DetailsInfoAdapter()
		fragment_detail_page_detail_info.apply {
			layoutManager =
				LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
			adapter = detailsInfoAdapter
		}
	}

	private fun initObserver() {
		viewModel.eventDetailsStateLiveData.observe(viewLifecycleOwner, ::handleState)
		viewModel.orderTicketStateLiveData.observe(viewLifecycleOwner, ::handleOrderState)
	}

	private fun handleOrderState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
				val fragment = OrderTicketScreenRouter.getTicketOrderStepFragment(state.value as? String ?: "")
				fragment.arguments = Bundle().apply {
					putInt(ORDER_DATE_TIME_ID, 168)
				}
				ft.add(R.id.nav_host_fragment_activity_main, fragment)
				ft.addToBackStack("")
				ft.commit()
			}
		}
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? EventDetailDataModel ?: return
				eventDetailDataModel = response

				val name = response.name.toString()
				fragment_detail_page_header_title.text = name

				response.images?.forEach {
					images.add(SlideModel(it))
				}
				fragment_detail_page_carousel.setImageList(images, ScaleTypes.CENTER_CROP)

				response.datetimes?.forEach {
					dateTimes.add(DateTime(it.id, it.date, it.start, it.duration))
				}
				dateTimesAdapter.setData(dateTimes)

				detailsInformation.add(
					DetailsInfoDataModel(
						R.drawable.ic_user,
						"Одинчоное мероприятие",
						"no data",
						""
					)
				)

				val languages = response.languages?.joinToString { it!! }.toString()

				detailsInformation.add(
					DetailsInfoDataModel(
						R.drawable.ic_message,
						"Язык",
						languages,
						""
					)
				)

				val price = response.price.toString()

				detailsInformation.add(
					DetailsInfoDataModel(
						R.drawable.ic_ticket,
						price,
						"Цена билета фиксированная",
						""
					)
				)

				val country = response.location?.country.toString()
				val location = response.location?.city.toString()
				val address = response.location?.apartment.toString()

				detailsInformation.add(
					DetailsInfoDataModel(
						R.drawable.ic_geo,
						"$location, $country",
						address,
						""
					)
				)

				Log.d("detinfo", detailsInformation.toString())

				detailsInfoAdapter.setData(detailsInformation)

				val findingInformation = response.location?.findingInformation
				if (findingInformation != null) {
					fragment_detail_page_location_address.text = findingInformation
				} else {
					fragment_detail_page_location_address.text = "$country, $location, $address"
				}

				val requirements = "Минимальный возраст: ${response.requirements?.age.toString()}" +
						"\nДополнительные требования: ${response.requirements?.additional_requirements.toString()}"
				fragment_detail_page_who_can_participate_requirements.text = requirements

				val description = response.description.toString()
				fragment_detail_page_program_of_event_activities.text = description

				val additionalServices = response.additional_services.toString()
				fragment_detail_page_organizer_services_info.text = additionalServices

				response.necessary_items?.forEach {
					checkList.add(CheckListDataModel(R.drawable.ic_check, it.toString()))
				}
				checkListAdapter.setData(checkList)

				val organizationImage = response.organization?.image.toString()
				val organizationName = response.organization?.name.toString()
				val organizationDescription = response.organization?.description.toString()
				Glide.with(this)
					.asBitmap()
					.load(organizationImage)
					.apply(RequestOptions.circleCropTransform())
					.into(fragment_detail_page_organizer_avatar)
				fragment_detail_page_organizer_name.text = organizationName
				fragment_detail_page_organizer_description.text = organizationDescription

				fragment_detail_page_contacts_phone_number.setOnClickListener {
					val organizationPhoneNumber = "tel:+7${response.organization?.phone_number}"
					val phoneNumberIntent = Intent(Intent.ACTION_DIAL, Uri.parse(organizationPhoneNumber))
					startActivity(phoneNumberIntent)
				}

				fragment_detail_page_contacts_whatsapp.setOnClickListener {
					val organizationWhatsapp = response.organization?.whatsapp
					if (organizationWhatsapp != null) {
						if (isAppInstalled("com.whatsapp", requireContext().packageManager)) {
							val organizationWhatsappUri =
								"https://wa.me/send?phone=+7${response.organization.whatsapp}&text=Здраствуйте! Пишу из приложения DvizhGO.}"
							openLink(organizationWhatsappUri)
						} else {
							Snackbar.make(
								requireView(),
								"WhatsApp не установлен",
								Snackbar.LENGTH_LONG
							).show()
						}
					}
				}

				fragment_detail_page_contacts_instagram.setOnClickListener {
					val organizationInstagram = response.organization?.instagram
					val organizationInstagramUri = "https://www.instagram.com/${organizationInstagram}"
					openLink(organizationInstagramUri)
				}

				fragment_detail_page_contacts_google.setOnClickListener {
					val organizationGmail = response.organization?.email
					val googleIntent = Intent(Intent.ACTION_SENDTO)
					googleIntent.setDataAndType(Uri.parse("mailto:$organizationGmail"), "message/rfc822")
					googleIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(organizationGmail))
					startActivity(Intent.createChooser(googleIntent, "Choose email client:"))
				}
			}
		}
	}

	private fun openLink(link: String) {
		val uri = Uri.parse(link)
		val intent = Intent(Intent.ACTION_VIEW)
		val flag = Intent.FLAG_ACTIVITY_NEW_TASK
		intent.data = uri
		intent.flags = flag
		startActivity(intent)
	}

	private fun isAppInstalled(packageName: String, pm: PackageManager): Boolean {
		return try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
			true
		} catch (ex: PackageManager.NameNotFoundException) {
			false
		}
	}
}