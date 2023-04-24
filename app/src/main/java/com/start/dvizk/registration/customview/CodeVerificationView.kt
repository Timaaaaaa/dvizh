package com.start.dvizk.registration.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import com.start.dvizk.R

class CodeVerificationView @JvmOverloads constructor(
		context: Context,
		attrs: AttributeSet? = null,
		defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

	private val editText1: EditText
	private val editText2: EditText
	private val editText3: EditText
	private val editText4: EditText

	init {
		LayoutInflater.from(context).inflate(R.layout.custom_view_code_verification, this, true)

		editText1 = findViewById(R.id.edit_text_1)
		editText2 = findViewById(R.id.edit_text_2)
		editText3 = findViewById(R.id.edit_text_3)
		editText4 = findViewById(R.id.edit_text_4)

		editText1.addTextChangedListener(
				CodeVerificationTextWatcher(editText1, editText2, null)
		)
		editText2.addTextChangedListener(
				CodeVerificationTextWatcher(editText2, editText3, editText1)
		)
		editText3.addTextChangedListener(
				CodeVerificationTextWatcher(editText3, editText4, editText2)
		)
		editText4.addTextChangedListener(
				CodeVerificationTextWatcher(editText4, null, editText3)
		)
	}

	private inner class CodeVerificationTextWatcher(
			private val currentEditText: EditText,
			private val nextEditText: EditText?,
			private val previousEditText: EditText?
	) : TextWatcher {

		private var isDeleting = false

		override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			if (count == 0 && after == 0 && previousEditText != null) {
				previousEditText.requestFocus()
			}
			isDeleting = count > after
		}

		override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
		}

		override fun afterTextChanged(s: Editable?) {
			if (isDeleting) {
				if (s?.length == 0 && previousEditText != null) {
					previousEditText.requestFocus()
					if (previousEditText.text.isNotEmpty()) {
						previousEditText.setSelection(previousEditText.text.length)
					}
				}
			} else {
				if (s?.length == 1 && nextEditText != null) {
					nextEditText.requestFocus()
				} else if (s?.length == 0 && previousEditText != null) {
					previousEditText.requestFocus()
					if (previousEditText.text.isNotEmpty()) {
						previousEditText.setSelection(previousEditText.text.length)
					}
				}
			}
		}
	}

	fun getCode(): String {
		return editText1.text.toString() + editText2.text.toString() +editText3.text.toString() +editText4.text.toString()
	}

}
