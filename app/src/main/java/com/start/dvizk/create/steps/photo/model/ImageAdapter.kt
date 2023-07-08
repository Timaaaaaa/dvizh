package com.start.dvizk.create.steps.photo.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.start.dvizk.R

class ImageAdapter() : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

	val images: MutableList<PhotoResponse> = mutableListOf()

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		val imageView: ImageView = view.findViewById(R.id.item_additional_image)
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val imageUrl = images[position]
		Glide.with(holder.itemView.context)
			.load(imageUrl.data.image)
			.into(holder.imageView)
	}

	override fun getItemCount(): Int {
		return images.size
	}

	fun setImages(newImages: List<PhotoResponse>) {
		images.clear()
		images.addAll(newImages)
		notifyDataSetChanged()
	}
}