package com.start.dvizk.main.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.start.dvizk.R

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
			Notification(R.drawable.ic_calendar_accent, true, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык. Для некоторых языков пользователям предлагаются варианты переводов, например далее"),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык."),
			Notification(R.drawable.ic_message, false, "Бронирование успешно", "23 Август, 2022", "Google Переводчик — веб-служба компании Google, предназначенная для автоматического перевода части текста или веб-страницы на другой язык.")
		)

		val adapter = NotificationAdapter(notifications)
		fragment_notifications_notifications.adapter = adapter
	}
}