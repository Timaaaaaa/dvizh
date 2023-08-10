package com.start.dvizk.create.steps.bottomsheet

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Category

class CategoryCheckAdapter(
	private val resources: Resources
):  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var categories = listOf<Category>()
	private var listener: OnCategoryCheckItemClickListener? = null

	private val CATEGORY_WITH_SUB_CAT = 1
	private val CATEGORY_WITHOUT_SUB_CAT = 2

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


		return when (viewType) {
			CATEGORY_WITH_SUB_CAT -> {
				val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_check, parent, false)
				ViewHolder1(view, resources)
			}
			CATEGORY_WITHOUT_SUB_CAT -> {
				val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subcategory_check, parent, false)
				ViewHolder2(view, resources)
			}
			else -> throw IllegalArgumentException("Invalid view type")
		}
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val item = categories[position]
		when (holder) {
			is ViewHolder1 -> {
				holder.bind(item)
			}
			is ViewHolder2 -> {
				holder.bind(item)
			}
			else -> throw IllegalArgumentException("Invalid view holder type")
		}
	}

	override fun getItemCount(): Int {
		return categories.size
	}

	override fun getItemViewType(position: Int): Int {
		return if (categories[position].is_sub) {
			CATEGORY_WITH_SUB_CAT
		} else {
			CATEGORY_WITHOUT_SUB_CAT
		}
	}

	fun setListener(listener: OnCategoryCheckItemClickListener) {
		this.listener = listener
	}

	fun setData(categories: List<Category>) {
		this.categories = categories
		notifyDataSetChanged()
	}

	inner class ViewHolder1(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

		private var title: TextView = itemView.findViewById(R.id.check_text)

		fun bind(organization: Category) {
			title.text = organization.name
			itemView.setOnClickListener {
				listener?.onItemClick(organization)
			}
		}
	}

	inner class ViewHolder2(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

		private var title: TextView = itemView.findViewById(R.id.check_text)
		private var checkBox: CheckBox = itemView.findViewById(R.id.checkbox)

		fun bind(organization: Category) {
			title.text = organization.name
			itemView.setOnClickListener {
				checkBox.performClick()
				listener?.onItemClick(organization)
			}
		}
	}
}