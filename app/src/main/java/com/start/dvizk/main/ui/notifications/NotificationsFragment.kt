package com.start.dvizk.main.ui.notifications

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R
import com.start.dvizk.auth.profile.ProfileAuthFragment
import com.start.dvizk.databinding.FragmentNotificationsBinding
import com.start.dvizk.main.ui.home.presentation.HomeFragment

class NotificationsFragment : Fragment() {

	private lateinit var fragment_notifications_notifications: RecyclerView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		return inflater.inflate(R.layout.fragment_notifications, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.findViewById<ImageView>(R.id.fragment_notifications_return_button).setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		fragment_notifications_notifications = view.findViewById(R.id.fragment_notifications_notifications)

		val notifications = listOf(
			Notification(R.drawable.ic_calendar, true, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык. Для некоторых языков пользователям предлагаются варианты переводов, например далее"),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык.")
		)

		val adapter = NotificationAdapter(notifications)
		fragment_notifications_notifications.adapter = adapter
	}
}