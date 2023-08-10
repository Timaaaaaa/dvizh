package com.start.dvizk.create.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.start.dvizk.R
import com.start.dvizk.registration.dialog.GenderSelectionListener

const val SUCCESS_DIALOG_TITLE: String = "success_dialog_title"
const val SUCCESS_DIALOG_SUBTITLE: String = "success_dialog_subtitle"

class SuccessDialog : DialogFragment() {

    private var listener: OnSuccessDialogOk? = null

    private lateinit var fragment_event_page_location_text: TextView
    private lateinit var fragment_event_page_location_text_1: TextView

    private lateinit var fragment_create_organization_next: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return inflater.inflate(R.layout.dialog_success, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragment_event_page_location_text =
            view.findViewById(R.id.fragment_event_page_location_text)
        fragment_event_page_location_text_1 =
            view.findViewById(R.id.fragment_event_page_location_text_1)

        fragment_create_organization_next =
            view.findViewById(R.id.fragment_create_organization_next)

        arguments?.getString(SUCCESS_DIALOG_TITLE)?.let {
            fragment_event_page_location_text.text = it
        }
        arguments?.getString(SUCCESS_DIALOG_SUBTITLE)?.let {
            fragment_event_page_location_text_1.text = it
        }

        fragment_create_organization_next.setOnClickListener {
            dismiss()
            if (listener != null) {
                listener?.onPositiveClickButton()

                return@setOnClickListener
            }
            activity?.finish()
        }
    }

    fun setListener(listener: OnSuccessDialogOk) {
        this.listener = listener
    }

    override fun show(manager: FragmentManager, tag: String?) {
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.add(this, tag)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun show(manager: FragmentManager) {
        show(manager, null)
    }
}