package com.start.dvizk.create.steps.bottomsheet

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.CategoriesListState
import org.koin.android.ext.android.inject

class BottomSheetSelectCategoryListFragment : BottomSheetDialogFragment() {

	lateinit var adapter: CategoryCheckAdapter
	private val viewModel: BottomSheetSelectCategoryListViewModel by inject()
	private lateinit var categoryRecyclerView: RecyclerView
	private lateinit var backButton: ImageView
	private lateinit var doneButton: Button
	private lateinit var listener: OnSelectCategoryBottomSheetDismiss

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_bottom_sheet_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.categoriesListState.observe(viewLifecycleOwner, ::categoriesListInit)
		viewModel.getCategories(null)
	}

	override fun onDismiss(dialog: DialogInterface) {
		listener.onDismissGetCategoryList(viewModel.categoryList)
		super.onDismiss(dialog)
	}

	fun setListener(listener: OnSelectCategoryBottomSheetDismiss) {
		this.listener = listener
	}

	private fun initView(view: View) {
		categoryRecyclerView = view.findViewById(R.id.fragment_bottom_sheet_category)
		backButton = view.findViewById(R.id.fragment_notifications_return_button)
		doneButton = view.findViewById(R.id.fragment_create_organization_next)
		doneButton.setOnClickListener {
			dismiss()
		}
		categoryRecyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		adapter = CategoryCheckAdapter(resources)
		adapter.setListener(viewModel)
		categoryRecyclerView.adapter = adapter

		backButton.setOnClickListener {
			viewModel.navigateBack()
		}
	}

	private fun categoriesListInit(state: CategoriesListState) {
		when (state) {
			is CategoriesListState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is CategoriesListState.Loading -> {
			}
			is CategoriesListState.Success -> {
				adapter.setData(state.categories)
			}
		}
	}
}