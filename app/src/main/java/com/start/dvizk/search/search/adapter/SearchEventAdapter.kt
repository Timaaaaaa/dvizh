package com.start.dvizk.search.search.adapter

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

class SearchEventAdapter(private val list: List<Event>, val resources: Resources) : RecyclerView.Adapter<SearchEventAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val favoriteEvent = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_favorite_event, parent, false)

		return ViewHolder(favoriteEvent)
	}

	override fun getItemCount(): Int {
		return list.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val event = list[position]
		holder.bind(event)
	}

	inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		private var image: ImageView = view.findViewById(R.id.item_favorite_event_image)
		private var title: TextView = view.findViewById(R.id.item_favorite_event_title)
		private var date: TextView = view.findViewById(R.id.item_favorite_event_date)
		private var location: TextView = view.findViewById(R.id.item_favorite_event_location)
		private var isFavorite: ImageView = view.findViewById(R.id.item_favorite_event_is_favorite_icon)

		fun bind(event: Event) {

			Glide.with(itemView)
				.load(event.main_image)
				.transform(MultiTransformation(CenterCrop(), RoundedCorners(resources.getDimensionPixelSize(R.dimen.big_event_default_image_radius))))
				.into(image)
			title.text = event.name
			date.text = getDate(event.datetime)
			location.text = getLocation(event.location)

			if (event.is_favorite) {
				isFavorite.setImageResource(R.drawable.ic_like_accent_filled)
			} else {
				isFavorite.setImageResource(R.drawable.ic_like_accent)
			}

			isFavorite.setOnClickListener {
				event.is_favorite = !event.is_favorite
				notifyDataSetChanged()
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