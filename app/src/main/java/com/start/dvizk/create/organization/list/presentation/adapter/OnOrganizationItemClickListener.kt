package com.start.dvizk.create.organization.list.presentation.adapter

import com.start.dvizk.create.organization.list.presentation.model.Organization
import com.start.dvizk.main.ui.home.presentation.model.Category
import com.start.dvizk.main.ui.home.presentation.model.Event

interface OnOrganizationItemClickListener {
	fun onItemClick(data: Organization)
}