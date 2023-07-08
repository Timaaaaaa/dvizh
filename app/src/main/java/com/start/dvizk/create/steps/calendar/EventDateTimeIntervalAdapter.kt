package com.start.dvizk.create.steps.calendar

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.create.steps.calendar.model.EventDateTimeInterval

class EventDateTimeIntervalAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

	private var items = listOf<EventDateTimeInterval>()
	private var listener: OnSelectTimeListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_time_interval, parent, false)
		return ViewHolder(view, resources)
	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val item = items[position]
		when (holder) {
			is ViewHolder -> {
				holder.bind(item)
			}
			else -> throw IllegalArgumentException("Invalid view holder type")
		}
	}

	override fun getItemCount(): Int {
		return items.size
	}

	fun setListener(listener: OnSelectTimeListener) {
		this.listener = listener
	}

	fun setData(items: List<EventDateTimeInterval>) {
		this.items = items
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View, val resources: Resources) :
		RecyclerView.ViewHolder(itemView) {

		private var date: TextView = itemView.findViewById(R.id.fragment_create_organization_title_5)
		private var duration: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinner)
		private var startTime: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinn)

		fun bind(item: EventDateTimeInterval) {
			date.text = item.date
			if (!item.durationViewText.isNullOrEmpty()) {
				duration.text = item.durationViewText
			} else {
				duration.text = ""
				duration.hint = "Выбрать время"
			}

			if (!item.startTime.isNullOrEmpty()) {
				startTime.text = item.startTime
			} else {
				startTime.text = ""
				startTime.hint = "Выбрать время"
			}

			duration.setOnClickListener {
				listener?.onDurationTimeSelect(item)
			}

			startTime.setOnClickListener {
				listener?.onStartTimeSelect(item)
			}
		}
	}
}