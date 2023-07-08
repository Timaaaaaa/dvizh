package com.start.dvizk.create.steps.bottomsheet.universal

import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event

interface OnSelectListClickListener {

	fun onItemSelect(id: Int)

	fun onMultiItemsSelect(id: Int)
}