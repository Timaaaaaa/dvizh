package com.start.dvizk.main.ui.notifications

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.start.dvizk.databinding.FragmentNotificationsBinding


class NotificationsFragment : Fragment() {

	private var _binding: FragmentNotificationsBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val notificationsViewModel =
			ViewModelProvider(this).get(NotificationsViewModel::class.java)

		_binding = FragmentNotificationsBinding.inflate(inflater, container, false)
		val root: View = binding.root

		val textView: TextView = binding.textNotifications
		notificationsViewModel.text.observe(viewLifecycleOwner) {
			textView.text = it
		}

		val handler = Handler()

// Delay the execution of someMethod by 2 seconds
		handler.postDelayed({
			textView.text = "asdfsdf"
		}, 2000)

		return root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}