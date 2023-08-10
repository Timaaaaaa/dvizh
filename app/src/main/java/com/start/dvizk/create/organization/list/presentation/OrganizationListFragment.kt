package com.start.dvizk.create.organization.list.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.create.presentation.CreateOrgonizationFragment
import com.start.dvizk.create.organization.create.presentation.model.CurrentStepState
import com.start.dvizk.create.organization.list.presentation.adapter.OrganizationAdapter
import com.start.dvizk.create.organization.list.presentation.model.OrganizationListState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

const val STEP_NUMBER_KEY = "step_number_key"
const val EVENT_ID_KEY = "event_id_key"
const val SPECIFIC_DATA_KEY = "SPECIFIC_DATA_KEY"

class OrganizationListFragment : Fragment() {

	private val viewModel: OrganizationsListViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var createOrganization: Button
	private lateinit var backButton: Button
	private lateinit var nextButton: Button
	private lateinit var fragment_create_organization_back_image: ImageView
	private lateinit var upcomingEventsRecyclerView: RecyclerView

	private lateinit var defaultEventAdapter: OrganizationAdapter

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_organizations_list, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)

		viewModel.organizationListStateLiveData.observe(viewLifecycleOwner, ::popularListInit)
		viewModel.currentStepStateLiveData.observe(viewLifecycleOwner, ::popularListInit)

		viewModel.getOrganizationList(SharedPreferencesRepository(requireContext()).getUserId().toInt())
	}

	private fun initView(view: View) {

		createOrganization = view.findViewById(R.id.fragment_create_organization_button)
		fragment_create_organization_back_image = view.findViewById(R.id.fragment_create_organization_back_image)
		upcomingEventsRecyclerView = view.findViewById(R.id.fragment_create_organization_list)
		backButton = view.findViewById(R.id.fragment_create_organization_back)
		nextButton = view.findViewById(R.id.fragment_create_organization_next)

		upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		defaultEventAdapter = OrganizationAdapter(resources)
		defaultEventAdapter.setListener(viewModel)
		upcomingEventsRecyclerView.adapter = defaultEventAdapter

		createOrganization.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val homeFragment = CreateOrgonizationFragment()
			ft.add(R.id.fragment_container,homeFragment)
			ft.addToBackStack("OrganizationListFragment")
			ft.commit()
		}
		nextButton.setOnClickListener {
			viewModel.getCurrentStep(token = sharedPreferencesRepository.getUserToken())
		}
		backButton.setOnClickListener {
			activity?.finish()
		}
		fragment_create_organization_back_image.setOnClickListener {
			activity?.finish()
		}
	}

	private fun popularListInit(state: OrganizationListState) {
		when (state) {
			is OrganizationListState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is OrganizationListState.Loading -> {
			}
			is OrganizationListState.Success -> {
				defaultEventAdapter.setData(state.organizations)
			}
		}
	}

	private fun popularListInit(state: CurrentStepState) {
		when (state) {
			is CurrentStepState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is CurrentStepState.Loading -> {
			}
			is CurrentStepState.Success -> {
				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(state.step.name)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, state.step.number_step)
					putInt(EVENT_ID_KEY, state.step.event_id)
				}
				ft.add(R.id.fragment_container,fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}
}