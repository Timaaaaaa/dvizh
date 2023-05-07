package com.start.dvizk.di

import android.app.Application
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.data.OrganizationListApi
import com.start.dvizk.create.organization.list.data.OrganizationListRepository
import com.start.dvizk.create.organization.list.presentation.OrganizationsListModel
import com.start.dvizk.main.ui.home.data.HomePageApi
import com.start.dvizk.main.ui.home.data.HomePageRepository
import com.start.dvizk.main.ui.home.presentation.HomeViewModel
import com.start.dvizk.network.ApiErrorExceptionFactory
import com.start.dvizk.network.DefaultApiErrorExceptionFactory
import com.start.dvizk.registration.createpassword.presentation.PasswordGenerationViewModel
import com.start.dvizk.registration.createpassword.domain.PasswordGenerationRepository
import com.start.dvizk.registration.registr.data.RegistrationApi
import com.start.dvizk.registration.varification.data.VerificationApi
import com.start.dvizk.registration.registr.data.RegistrationRepository
import com.start.dvizk.registration.registr.domain.VerificationRepository
import com.start.dvizk.registration.registr.presentation.RegistrationViewModel
import com.start.dvizk.registration.varification.presentation.VerificationViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal const val APP_RETROFIT = "app_retrofit"
internal const val APP_RETROFIT_HTTP_CLIENT = "app_retrofit_http_client"

object DiContainer {

	private val networkModule = module {

		single { getOkHttpClient() }

		single(named(APP_RETROFIT)) { getRetrofit(get()) }

		factory {
			val appRetrofit: Retrofit = get(named(APP_RETROFIT))
			RegistrationRepository(
				registrationApi = appRetrofit.create(RegistrationApi::class.java),
				apiErrorExceptionFactory = get()
			)
		}

		factory {
			val appRetrofit: Retrofit = get(named(APP_RETROFIT))
			HomePageRepository(
				homePageApi = appRetrofit.create(HomePageApi::class.java),
				apiErrorExceptionFactory = get()
			)
		}

		factory {
			val appRetrofit: Retrofit = get(named(APP_RETROFIT))
			VerificationRepository(
				verificationApi = appRetrofit.create(VerificationApi::class.java),
				apiErrorExceptionFactory = get()
			)
		}

		factory {
			val appRetrofit: Retrofit = get(named(APP_RETROFIT))
			PasswordGenerationRepository(
				registrationApi = appRetrofit.create(RegistrationApi::class.java),
				apiErrorExceptionFactory = get()
			)
		}

		factory {
			val appRetrofit: Retrofit = get(named(APP_RETROFIT))
			OrganizationListRepository(
				organizationListApi = appRetrofit.create(OrganizationListApi::class.java),
			)
		}

		factory<ApiErrorExceptionFactory> {
			DefaultApiErrorExceptionFactory() as ApiErrorExceptionFactory
		}
	}

	fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
		return Retrofit.Builder()
			.baseUrl("http://161.35.145.58")
			.addConverterFactory(GsonConverterFactory.create())
			.client(okHttpClient)
			.build()
	}

	fun getOkHttpClient(): OkHttpClient {
		val interceptor = HttpLoggingInterceptor()
		interceptor.level = HttpLoggingInterceptor.Level.BODY
		return OkHttpClient.Builder()
			.followRedirects(true)
			.followSslRedirects(true)
			.retryOnConnectionFailure(true)
			.cache(null)
			.addInterceptor(interceptor)
//            .addInterceptor {
//                val requestBuilder = it.request().newBuilder()
//                        .addHeader("Authorization", "Bearer ${EmployeeConstant.TOKEN}")
//                it.proceed(requestBuilder.build())
//            }
			.connectTimeout(400, TimeUnit.SECONDS)
			.writeTimeout(400, TimeUnit.SECONDS)
			.readTimeout(400, TimeUnit.SECONDS)
			.build()
	}

	val viewModelModule: Module = module {

		viewModel {
			RegistrationViewModel(
				registrationRepository = get()
			)
		}

		viewModel {
			HomeViewModel(
				homePageRepository = get()
			)
		}

		viewModel {
			VerificationViewModel(
				verificationRepo = get(),
				sharedPreferencesRepository = get()
			)
		}

		viewModel {
			PasswordGenerationViewModel(
				passwordGenerationRepository = get()
			)
		}

		viewModel {
			OrganizationsListModel(
				organizationListRepository = get()
			)
		}
	}

	val mainModule: Module = module {

		factory {
			SharedPreferencesRepository(androidContext())
		}
	}

	fun startKoinDi(application: Application) {
		startKoin {
			androidContext(application)
			modules(
				viewModelModule,
				networkModule,
				mainModule
			)
		}
	}
}
