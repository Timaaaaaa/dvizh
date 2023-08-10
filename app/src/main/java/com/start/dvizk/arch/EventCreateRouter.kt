package com.start.dvizk.arch

import androidx.fragment.app.Fragment
import com.start.dvizk.create.dialog.SuccessDialog
import com.start.dvizk.create.steps.about.AboutStepFragment
import com.start.dvizk.create.steps.booking.BookingStepFragment
import com.start.dvizk.create.steps.calendar.CalendarStepFragment
import com.start.dvizk.create.steps.cancelrule.CancelRuleStepFragment
import com.start.dvizk.create.steps.category.CategoryStepFragment
import com.start.dvizk.create.steps.classification.ClassificationStepFragment
import com.start.dvizk.create.steps.eventrule.EventRuleStepFragment
import com.start.dvizk.create.steps.freeorpay.FreeOrPayStepFragment
import com.start.dvizk.create.steps.guestcount.GusetCountStepFragment
import com.start.dvizk.create.steps.language.LanguageStepFragment
import com.start.dvizk.create.steps.language.LanguageStepViewModel
import com.start.dvizk.create.steps.location.LocationStepFragment
import com.start.dvizk.create.steps.name.NameStepFragment
import com.start.dvizk.create.steps.needings.NeededItemsStepFragment
import com.start.dvizk.create.steps.photo.PhotoStepFragment
import com.start.dvizk.create.steps.price.PriceStepFragment
import com.start.dvizk.create.steps.service.AdditionalServiceStepFragment
import com.start.dvizk.create.steps.type.presentation.TypeStepFragment
import com.start.dvizk.create.steps.visitperson.AllowedGuestStepFragment

object EventCreateRouter {

	fun getCreateStepFragment(stepName: String?): Fragment = when (stepName) {
		"type" -> {
			TypeStepFragment()
		}
		"language" -> {
			LanguageStepFragment()
		}
		"category" -> {
			CategoryStepFragment()
		}
		"description" -> {
			AboutStepFragment()
		}
		"regionAndLocation" -> {
			LocationStepFragment()
		}
		"datetime" -> {
			CalendarStepFragment()
		}
		"purchaseDeadline" -> {
			BookingStepFragment()
		}
		"additionalServices" -> {
			AdditionalServiceStepFragment()
		}
		"necessaryItems" -> {
			NeededItemsStepFragment()
		}
		"requirement" -> {
			AllowedGuestStepFragment()
		}
		"images" -> {
			PhotoStepFragment()
		}
		"name" -> {
			NameStepFragment()
		}
		"classification" -> {
			ClassificationStepFragment()
		}
		"maximumGroupSize" -> {
			GusetCountStepFragment()
		}
		"entryCondition" -> {
			FreeOrPayStepFragment()
		}
		"price" -> {
			PriceStepFragment()
		}
		"cancellationRules" -> {
			CancelRuleStepFragment()
		}
		"rules" -> {
			EventRuleStepFragment()
		}
		"success" -> {
			SuccessDialog()
		}
		else -> {
			TypeStepFragment()
		}
	}
}