package com.start.dvizk.main.ui.home.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.start.dvizk.R
import com.start.dvizk.main.ui.home.presentation.model.Event
import com.start.dvizk.main.ui.home.presentation.model.EventDateTime
import com.start.dvizk.main.ui.home.presentation.model.EventLocation
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.Month
import java.time.format.DateTimeFormatter

class BigEventAdapter(
	private val resources: Resources
): RecyclerView.Adapter<BigEventAdapter.ViewHolder>() {

	private var events = listOf<Event>()
	private var listener: OnItemClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
		return ViewHolder(view, resources)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = events[position]
		holder.bind(item, listener)
	}

	override fun getItemCount(): Int {
		return events.size
	}

	fun setData(events: List<Event>) {
		this.events = events
		notifyDataSetChanged()
	}

	fun setListener(listener: OnItemClickListener) {
		this.listener = listener
	}

	class ViewHolder(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

		private var image: ImageView = itemView.findViewById(R.id.item_event_image)
		private var title: TextView = itemView.findViewById(R.id.item_event_title_text_view)
		private var data: TextView = itemView.findViewById(R.id.item_event_subtitle_text_view)
		private var address: TextView = itemView.findViewById(R.id.item_event_address_text_view)

		fun bind(event: Event, listener: OnItemClickListener?) {

			Glide.with(itemView)
				.load(event.main_image)
				.transform(MultiTransformation(FitCenter(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.big_event_default_image_radius))))
				.into(image)
			title.text = event.name
			data.text = getDate(event.datetime)
			address.text = getLocation(event.location)

			itemView.setOnClickListener {
				listener?.onItemClick(event)
			}
		}

		private fun getDate(dateTime: EventDateTime): String {
			val dateString = dateTime.start
			val durationString = dateTime.duration

			val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
			val date = LocalDate.parse(dateString.substring(0, 10), dateFormatter)

			val formattedDayOfWeek = when (date.dayOfWeek) {
				DayOfWeek.MONDAY -> "Пн"
				DayOfWeek.TUESDAY -> "Вт"
				DayOfWeek.WEDNESDAY -> "Ср"
				DayOfWeek.THURSDAY -> "Чт"
				DayOfWeek.FRIDAY -> "Пт"
				DayOfWeek.SATURDAY -> "Сб"
				DayOfWeek.SUNDAY -> "Вс"
				else -> "ERROR"
			}

			val dayOfMonth = date.dayOfMonth
			val formattedMonth = when (date.month) {
				Month.SEPTEMBER -> "Сентября"
				Month.OCTOBER -> "Октября"
				Month.NOVEMBER -> "Ноября"
				Month.DECEMBER -> "Декабря"
				Month.JANUARY -> "Января"
				Month.FEBRUARY -> "Февраля"
				Month.MARCH -> "Марта"
				Month.APRIL -> "Апреля"
				Month.MAY -> "Мая"
				Month.JUNE -> "Июня"
				Month.JULY -> "Июля"
				Month.AUGUST -> "Августа"
				else -> "ERROR"
			}

			val startTime = LocalTime.parse(dateString.substring(11, dateString.length), DateTimeFormatter.ISO_LOCAL_TIME)
			val duration = LocalTime.parse(durationString, DateTimeFormatter.ISO_LOCAL_TIME)
			val endTime = startTime.plusHours(duration.hour.toLong()).plusMinutes(duration.minute.toLong())

			val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
			val formattedStartTime = startTime.format(timeFormatter)
			val formattedEndTime = endTime.format(timeFormatter)

			return "$formattedDayOfWeek, $dayOfMonth $formattedMonth · $formattedStartTime - $formattedEndTime"
		}

		private fun getLocation(location: EventLocation): String {
			val address = location.apartment
			val city = location.city.name
			return "$address, $city"
		}
	}
}