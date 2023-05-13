package com.start.dvizk.arch

import androidx.fragment.app.Fragment
import com.start.dvizk.create.steps.TypeStepFragment

object EventCreateRouter {

	fun getCreateStepFragment(stepName: String): Fragment = when (stepName) {
		"type" -> {
			TypeStepFragment()
		}
		"languages" -> {
			TypeStepFragment()
		}
		"category" -> {
			TypeStepFragment()
		}
		"description" -> {
			TypeStepFragment()
		}
		"regionAndLocation" -> {
			TypeStepFragment()
		}
		"datetime" -> {
			TypeStepFragment()
		}
		"purchaseDeadline" -> {
			TypeStepFragment()
		}
		"additionalServices" -> {
			TypeStepFragment()
		}
		"necessaryItems" -> {
			TypeStepFragment()
		}
		"name" -> {
			TypeStepFragment()
		}
		"images" -> {
			TypeStepFragment()
		}
		else -> {
			TypeStepFragment()
		}
	}
}