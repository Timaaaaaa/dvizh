package com.start.dvizk.create.steps.bottomsheet.universal.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectItem(
    val id: Int,
    val name: String,
    var isSelect: Boolean
): Parcelable