package com.start.dvizk.registration.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.start.dvizk.R

class GenderSelectionDialog : DialogFragment() {

    private lateinit var maleText: TextView
    private lateinit var femaleText: TextView

    private var genderSelectionListener: GenderSelectionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DefaultTransparentDialogStyle)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {

        return inflater.inflate(
                R.layout.dialog_gender_selection,
                container,
                false
        )
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        maleText = view.findViewById(R.id.gender_selection_dialog_male_text)
        femaleText = view.findViewById(R.id.gender_selection_dialog_female_text)
        maleText.setOnClickListener {
            genderSelectionListener?.getGender("male")
            dismiss()
        }
        femaleText.setOnClickListener {
            genderSelectionListener?.getGender("female")
            dismiss()
        }
    }

    fun setListener(genderSelectionListener: GenderSelectionListener) {
        this.genderSelectionListener = genderSelectionListener
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