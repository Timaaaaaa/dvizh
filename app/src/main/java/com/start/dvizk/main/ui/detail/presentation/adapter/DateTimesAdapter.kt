package com.start.dvizk.main.ui.detail.presentation.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.main.ui.detail.data.model.DateTime
import com.start.dvizk.main.ui.detail.presentation.OnDateTimeClickListener
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class DateTimesAdapter(
	private val resources: Resources
) : RecyclerView.Adapter<DateTimesAdapter.ViewHolder>() {

	private var dateTimes = listOf<DateTime>()
	private var listener: OnDateTimeClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val dateTime = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_date_time, parent, false)
		return ViewHolder(dateTime)
	}

	override fun getItemCount(): Int = dateTimes.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val dateTime = dateTimes[position]
		holder.bind(dateTime, listener)
	}

	fun setData(dateTimeS: List<DateTime>) {
		this.dateTimes = dateTimeS
		notifyDataSetChanged()
	}

	fun setListener(listener: OnDateTimeClickListener) {
		this.listener = listener
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

		val icon: ImageView = itemView.findViewById(R.id.item_date_time_icon)
		val title: TextView = itemView.findViewById(R.id.item_date_time_title)
		val subtitle: TextView = itemView.findViewById(R.id.item_date_time_subtitle)

		fun bind(dateTime: DateTime, listener: OnDateTimeClickListener?) {
			title.text = dateTime.date
			subtitle.text = getTime(dateTime)

			if (dateTime.isSelected) {
				icon.setImageResource(R.drawable.ic_calendar_accent)
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_date_time_card_selected, itemView.context.theme)
			} else {
				icon.setImageResource(R.drawable.ic_calendar_white)
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_date_time_card, itemView.context.theme)
			}

			itemView.setOnClickListener {
				dateTimes.forEach {
					it.isSelected = false
					if (it.id == dateTime.id) {
						it.isSelected = true
					}
				}
				notifyDataSetChanged()
				listener?.onItemClick(dateTime)
			}
		}

		private fun getTime(dateTime: DateTime): String {
			val timeRange = dateTime.duration?.substring(0,1)
			val formatter = DateTimeFormatter.ofPattern("HH:mm")
			val startTime = LocalTime.parse(dateTime.start, formatter)
			val endTime = timeRange?.let { startTime.plusHours(it.toLong()) }

			val formattedStartTime = startTime.format(formatter)
			val formattedEndTime = endTime?.format(formatter)

			return "$formattedStartTime - $formattedEndTime"
		}
	}
}