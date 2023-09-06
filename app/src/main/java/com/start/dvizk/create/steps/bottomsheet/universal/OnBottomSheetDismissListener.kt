package com.start.dvizk.create.steps.bottomsheet.universal

import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem

interface OnBottomSheetDismissListener {

	fun onBottomSheetDismiss(ids: List<Int>, parameterName: String = "", list: MutableList<SelectItem>)
}