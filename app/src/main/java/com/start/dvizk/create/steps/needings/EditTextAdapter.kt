package com.start.dvizk.create.steps.needings

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class EditTextAdapter(private val data: MutableList<String>) :
	RecyclerView.Adapter<EditTextAdapter.ViewHolder>() {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val editText: EditText = view.findViewById(R.id.edit_text)
		val delete: ImageView = view.findViewById(R.id.delete)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_needed_good, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.editText.setText(data[position])

		holder.editText.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
				// Called before the text is changed
			}

			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
				val currentPosition = holder.adapterPosition
				if (currentPosition in 0 until data.size) {
					data[currentPosition] = holder.editText.text.toString()
				}
			}

			override fun afterTextChanged(s: Editable?) {
				// Called after the text has been changed
			}
		})

		holder.delete.setOnClickListener {
			val currentPosition = holder.adapterPosition
			if (currentPosition != RecyclerView.NO_POSITION) {
				removeItem(currentPosition)
			}
		}
	}

	override fun getItemCount(): Int {
		return data.size
	}

	fun addItem() {
		data.add("")
		notifyItemInserted(data.size - 1)
	}

	fun getItems(): List<String> {
		return data
	}

	fun removeItem(position: Int) {
		if (position in 0 until data.size) {
			data.removeAt(position)
			notifyDataSetChanged()
		}
	}
}
