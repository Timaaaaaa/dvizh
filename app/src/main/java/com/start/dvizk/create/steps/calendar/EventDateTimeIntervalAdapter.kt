package com.start.dvizk.create.steps.calendar

import android.content.res.Resources
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
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

		return if (viewType == 1) {
			ViewHolderSingle(view, resources)
		} else {
			ViewHolderGroup(view, resources)
		}

	}

	override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
		val item = items[position]
		when (holder) {
			is ViewHolderSingle -> {
				holder.bind(item)
			}
			is ViewHolderGroup -> {
				holder.bind(item)
			}
			else -> throw IllegalArgumentException("Invalid view holder type")
		}
	}

	override fun getItemViewType(position: Int): Int {
		return if (items[position].type == "datetimeSingle") {
			1
		} else {
			2
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

	inner class ViewHolderSingle(itemView: View, val resources: Resources) :
		RecyclerView.ViewHolder(itemView) {

		private var date: TextView = itemView.findViewById(R.id.fragment_create_organization_title_5)
		private var duration: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinner)
		private var startTime: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinn)
		private var priceEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_1)
		private var ticketCountEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_2)

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



			priceEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

				override fun afterTextChanged(s: Editable?) {
					val price = s?.toString()?.toIntOrNull() ?: -1
					items[adapterPosition].price = price
				}
			})

			ticketCountEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

				override fun afterTextChanged(s: Editable?) {
					val count = s?.toString()?.toIntOrNull() ?: -1
					items[adapterPosition].ticketCount = count
				}
			})

			duration.setOnClickListener {
				listener?.onDurationTimeSelect(item)
			}

			startTime.setOnClickListener {
				listener?.onStartTimeSelect(item)
			}
		}
	}

	inner class ViewHolderGroup(itemView: View, val resources: Resources) :
		RecyclerView.ViewHolder(itemView) {

		private var title0: TextView = itemView.findViewById(R.id.fragment_create_organization_title_10)
		private var title1: TextView = itemView.findViewById(R.id.fragment_create_organization_title_11)
		private var title2: TextView = itemView.findViewById(R.id.fragment_create_organization_title_12)
		private var date: TextView = itemView.findViewById(R.id.fragment_create_organization_title_5)
		private var duration: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinner)
		private var startTime: TextView = itemView.findViewById(R.id.fragment_registration_user_gender_spinn)
		private var priceEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_1)
		private var ticketCountEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_2)
		private var teamCountEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_3)
		private var teamMemberCountEditText: EditText = itemView.findViewById(R.id.fragment_registration_user_gender_4)

		fun bind(item: EventDateTimeInterval) {

			ticketCountEditText.visibility = View.GONE
			title0.visibility = View.GONE
			teamCountEditText.visibility = View.VISIBLE
			teamMemberCountEditText.visibility = View.VISIBLE
			title1.visibility = View.VISIBLE
			title2.visibility = View.VISIBLE

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

			priceEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

				override fun afterTextChanged(s: Editable?) {
					val price = s?.toString()?.toIntOrNull() ?: -1
					items[adapterPosition].price = price
				}
			})

			teamCountEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

				override fun afterTextChanged(s: Editable?) {
					val count = s?.toString()?.toIntOrNull() ?: -1
					items[adapterPosition].teamCount = count
				}
			})

			teamMemberCountEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

				override fun afterTextChanged(s: Editable?) {
					val count = s?.toString()?.toIntOrNull() ?: -1
					items[adapterPosition].teamMemberCount = count
				}
			})
		}
	}
}