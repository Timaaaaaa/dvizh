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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.create.presentation.CreateOrgonizationFragment
import com.start.dvizk.create.organization.list.presentation.model.OrganizationListState
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrganizationListFragment : Fragment() {

	private val viewModel: OrganizationsListModel by viewModel()

	private lateinit var createOrganization: Button
	private lateinit var backButton: Button
	private lateinit var nextButton: Button
	private lateinit var fragment_create_organization_back_image: ImageView
	private lateinit var upcomingEventsRecyclerView: RecyclerView
	private lateinit var fragment_organization_list_refresh: SwipeRefreshLayout

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

		viewModel.getPopularEvents(SharedPreferencesRepository(requireContext()).getUserId().toInt())
	}

	fun refreshContent() {
	}

	private fun initView(view: View) {

		createOrganization = view.findViewById(R.id.fragment_create_organization_button)
		fragment_create_organization_back_image = view.findViewById(R.id.fragment_create_organization_back_image)
		fragment_organization_list_refresh = view.findViewById(R.id.fragment_organization_list_refresh)
		upcomingEventsRecyclerView = view.findViewById(R.id.fragment_create_organization_list)
		backButton = view.findViewById(R.id.fragment_create_organization_back)
		nextButton = view.findViewById(R.id.fragment_create_organization_next)

		upcomingEventsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		defaultEventAdapter = OrganizationAdapter(resources)
		upcomingEventsRecyclerView.adapter = defaultEventAdapter

		createOrganization.setOnClickListener {
			val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
			val homeFragment = CreateOrgonizationFragment()
			ft.add(R.id.fragment_container,homeFragment)
			ft.addToBackStack("OrganizationListFragment")
			ft.commit()
		}
		nextButton.setOnClickListener {

		}
		backButton.setOnClickListener {
			activity?.finish()
		}
		fragment_create_organization_back_image.setOnClickListener {
			activity?.finish()
		}
		fragment_organization_list_refresh.setOnRefreshListener {
			viewModel.getPopularEvents(SharedPreferencesRepository(requireContext()).getUserId().toInt())
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
}