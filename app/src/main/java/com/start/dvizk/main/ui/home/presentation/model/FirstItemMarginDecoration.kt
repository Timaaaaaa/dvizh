package com.start.dvizk.main.ui.home.presentation.model

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FirstItemMarginDecoration(private val firstItemOffset: Int, private val subsequentItemOffset: Int) : RecyclerView.ItemDecoration() {

	override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
		super.getItemOffsets(outRect, view, parent, state)

		val position = parent.getChildAdapterPosition(view)

		if (position == 0) {
			outRect.left = firstItemOffset
		} else {
			outRect.left = subsequentItemOffset
		}
	}
}