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

class SuccessDialog : DialogFragment() {

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

        return inflater.inflate(
                R.layout.dialog_success,
                container,
                false
        )
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        fragment_create_organization_next = view.findViewById(R.id.fragment_create_organization_next)

        fragment_create_organization_next.setOnClickListener {
            activity?.finish()
        }
    }

    fun setListener(genderSelectionListener: GenderSelectionListener) {

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