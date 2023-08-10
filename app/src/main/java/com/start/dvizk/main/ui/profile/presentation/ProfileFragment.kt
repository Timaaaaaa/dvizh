package com.start.dvizk.main.ui.profile.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.main.ui.home.presentation.HomeFragment
import com.start.dvizk.main.ui.profile.data.model.User
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {

	private val viewModel: ProfileViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_profile_page_profile_avatar: ImageView
	private lateinit var fragment_profile_page_profile_name: TextView

	private lateinit var fragment_profile_page_events_count: TextView
	private lateinit var fragment_profile_page_followers_count: TextView
	private lateinit var fragment_profile_page_subscriptions_count: TextView

	private lateinit var fragment_profile_page_logout: ConstraintLayout

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_profile_page, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObserver()

		viewModel.getUserProfile(sharedPreferencesRepository.getUserToken())
	}

	private fun initView(view: View) {
		fragment_profile_page_profile_avatar =
			view.findViewById(R.id.fragment_profile_page_profile_avatar)

		Glide.with(this)
			.load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTMHyHnkeJyo587A_tb63tvSMvEy9USpStzZA&usqp=CAU")
			.apply(RequestOptions.circleCropTransform())
			.into(fragment_profile_page_profile_avatar)


		fragment_profile_page_profile_name =
			view.findViewById(R.id.fragment_profile_page_profile_name)

		fragment_profile_page_events_count =
			view.findViewById(R.id.fragment_profile_page_events_count)
		fragment_profile_page_followers_count =
			view.findViewById(R.id.fragment_profile_page_followers_count)
		fragment_profile_page_subscriptions_count =
			view.findViewById(R.id.fragment_profile_page_subscriptions_count)

		fragment_profile_page_logout =
			view.findViewById(R.id.fragment_profile_page_logout)
		fragment_profile_page_logout.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()

			sharedPreferencesRepository.clearAll()
			ft.replace(R.id.nav_host_fragment_activity_main, HomeFragment())
			ft.commit()
		}
	}

	private fun initObserver() {
		viewModel.profileStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? User ?: return

				fragment_profile_page_profile_name.text = response.user.name

				fragment_profile_page_events_count.text = response.user.eventsCount.toString()
				fragment_profile_page_followers_count.text = response.user.subscribers.toString()
				fragment_profile_page_subscriptions_count.text = response.user.subscriptions.toString()
			}
		}
	}
}