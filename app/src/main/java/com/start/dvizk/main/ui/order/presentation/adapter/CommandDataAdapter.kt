package com.start.dvizk.main.ui.order.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

class CommandDataAdapter : RecyclerView.Adapter<CommandDataAdapter.ViewHolder>() {

	private var members = mutableListOf<String>()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		val member = LayoutInflater.from(parent.context)
			.inflate(R.layout.item_command_member, parent, false)

		return ViewHolder(member)
	}

	override fun getItemCount(): Int = members.size

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bind()
	}

	fun addItem() {
		val newMembers = ArrayList(members)
		newMembers.add("")
		members.clear()
		members.addAll(newMembers)
		notifyDataSetChanged()
	}

	fun removeItem(position: Int) {
		val newMembers = ArrayList(members)
		newMembers.removeAt(position)
		members.clear()
		members.addAll(newMembers)
		notifyDataSetChanged()
	}

	fun getData(): List<String> {
		return members
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind() {
			val memberEditText: EditText = itemView.findViewById(R.id.item_command_member_edit_text)
			val memberRemoveButton: ImageView = itemView.findViewById(R.id.item_command_member_remove)

			memberEditText.addTextChangedListener(object : TextWatcher {
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
				override fun afterTextChanged(s: Editable?) {
					members[adapterPosition] = memberEditText.text.toString()
				}
			})

			memberRemoveButton.setOnClickListener {
				removeItem(adapterPosition)
			}
		}
	}
}