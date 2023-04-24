package com.start.dvizk.launcher

import android.app.Application
import com.start.dvizk.di.DiContainer

class AppInitialyzer: Application() {

	override fun onCreate() {
		super.onCreate()
		DiContainer.startKoinDi(this)
	}
}