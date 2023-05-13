package com.start.dvizk.create.organization.list.presentation

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.create.organization.list.presentation.adapter.OnOrganizationItemClickListener
import com.start.dvizk.create.organization.list.presentation.model.Organization

class OrganizationAdapter(
	private val resources: Resources
): RecyclerView.Adapter<OrganizationAdapter.ViewHolder>() {

	private var events = listOf<Organization>()
	private var listener: OnOrganizationItemClickListener? = null

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_organization, parent, false)
		return ViewHolder(view, resources)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = events[position]
		holder.bind(item)
	}

	override fun getItemCount(): Int {
		return events.size
	}

	fun setListener(listener: OnOrganizationItemClickListener) {
		this.listener = listener
	}

	fun setData(events: List<Organization>) {
		this.events = events
		notifyDataSetChanged()
	}

	inner class ViewHolder(itemView: View, val resources: Resources) : RecyclerView.ViewHolder(itemView) {

		private var image: ImageView = itemView.findViewById(R.id.item_organization_image)
		private var title: TextView = itemView.findViewById(R.id.item_organization_title_text_view)
		private var description: TextView = itemView.findViewById(R.id.item_organization_subtitle_text_view)

		fun bind(organization: Organization) {
			if (organization.isSelected) {
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_light_grey_with_border_purple, itemView.context.theme)
			} else {
				itemView.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_item_event_default_grey, itemView.context.theme)
			}
			title.text = organization.name
			description.text = organization.description
			Glide.with(itemView)
				.load("http://161.35.145.58/"+organization.image)
				.apply(RequestOptions.circleCropTransform())
				.into(image)
			itemView.setOnClickListener {
				events.forEach{
					it.isSelected = false
					if(it.id == organization.id) {
						it.isSelected = true
					}
				}
				notifyDataSetChanged()
				listener?.onItemClick(organization)
			}
		}
	}
}