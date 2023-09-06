package com.start.dvizk.create.steps.bottomsheet.universal

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.start.dvizk.R
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem

const val IS_MULTI_SELECT_KEY = "IS_MULTI_SELECT_KEY"
const val SELECT_LIST_KEY = "SELECT_LIST_KEY"
const val PARAMETER_NAME_KEY = "PARAMETER_NAME_KEY"

class BottomSheetSelectListFragment : BottomSheetDialogFragment(), OnSelectListClickListener {

	lateinit var adapter: SelectListAdapter
	private var list = mutableListOf<SelectItem>()
	private lateinit var categoryRecyclerView: RecyclerView
	private lateinit var backButton: ImageView
	private lateinit var doneButton: Button
	private lateinit var fragment_notifications_header_text: TextView
	private var listener: OnBottomSheetDismissListener? = null

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
		categoriesListInit()
	}

	override fun onDismiss(dialog: DialogInterface) {
		var ids = listOf<Int>()
		list.forEach {
			if (it.isSelect) {
				ids = ids.plus(it.id)
			}
		}
		listener?.onBottomSheetDismiss(ids, arguments?.getString(PARAMETER_NAME_KEY) ?: "default", list.filter { it.isSelect }.toMutableList())
		listener = null
		adapter.setListener(null)
		super.onDismiss(dialog)
	}

	private fun initView(view: View) {
		categoryRecyclerView = view.findViewById(R.id.fragment_bottom_sheet_category)
		backButton = view.findViewById(R.id.fragment_notifications_return_button)
		fragment_notifications_header_text = view.findViewById(R.id.fragment_notifications_header_text)

		categoryRecyclerView.layoutManager =
			LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
		arguments?.apply {
			adapter = SelectListAdapter(resources, getBoolean(IS_MULTI_SELECT_KEY))
			fragment_notifications_header_text.text = getString("TITLE") ?: ""
		}

		adapter.setListener(this)
		categoryRecyclerView.adapter = adapter

		backButton.setOnClickListener {
			dismiss()
		}
		doneButton = view.findViewById(R.id.fragment_create_organization_next)
		doneButton.setOnClickListener {
			dismiss()
		}
	}

	private fun categoriesListInit() {
		arguments?.apply {
			list = (getParcelableArrayList<SelectItem>(SELECT_LIST_KEY)?.toMutableList() ?: emptyList<SelectItem>()) as MutableList<SelectItem>
			adapter.setData(list)
		}
	}

	override fun onItemSelect(id: Int) {
		var item: SelectItem? = null
		list.forEach {
			if(it.id == id) {
				it.isSelect = !it.isSelect
				item = it
			}
		}
		adapter.notifyItemChanged(list.indexOf(item))
		dismiss()
	}

	override fun onMultiItemsSelect(id: Int) {
		var item: SelectItem? = null
		list.forEach {
			if(it.id == id) {
				it.isSelect = !it.isSelect
				item = it
			}
		}
		adapter.notifyItemChanged(list.indexOf(item))
	}

	fun setListener(listener: OnBottomSheetDismissListener) {
		this.listener = listener
	}
}