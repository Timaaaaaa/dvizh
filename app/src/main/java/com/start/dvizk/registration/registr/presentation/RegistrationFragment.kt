package com.start.dvizk.registration.registr.presentation

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.registration.createpassword.presentation.PasswordGenerationFragment
import com.start.dvizk.registration.dialog.GenderSelectionDialog
import com.start.dvizk.registration.dialog.GenderSelectionListener
import com.start.dvizk.registration.registr.presentation.model.User
import com.start.dvizk.registration.varification.presentation.VerificationCodeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

private const val PICK_IMAGE_REQUEST = 1
private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

class RegistrationFragment :
	Fragment(),
	OnClickListener,
	GenderSelectionListener {

	private val registrationViewModel: RegistrationViewModel by viewModel<RegistrationViewModel>()

	private lateinit var genderSelectionText: TextView
	private lateinit var returnPage: ImageView
	private lateinit var profileImageLoader: ImageView
	private lateinit var dateOfBirth: TextView
	private lateinit var continueRegistrtion: Button
	private lateinit var fragment_registration_loader: View
	private lateinit var fragment_registration_user_name_edit_text: EditText
	private lateinit var fragment_registration_user_nickname_edit_text: EditText
	private lateinit var fragment_registration_user_email_edit_text: EditText
	private lateinit var fragment_registration_user_phone_edit_text: EditText

	var filePath = ""
	var birthday = ""
	var gender = ""

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {

		return inflater.inflate(R.layout.fragment_registration, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initObservers()
		fragment_registration_user_name_edit_text =
			view.findViewById(R.id.fragment_registration_user_name_edit_text)
		fragment_registration_user_nickname_edit_text =
			view.findViewById(R.id.fragment_registration_user_nickname_edit_text)
		fragment_registration_user_email_edit_text =
			view.findViewById(R.id.fragment_registration_user_email_edit_text)
		fragment_registration_user_phone_edit_text =
			view.findViewById(R.id.fragment_registration_user_phone_edit_text)
		genderSelectionText = view.findViewById(R.id.fragment_registration_user_gender_spinner)
		fragment_registration_loader = requireActivity().findViewById(R.id.progress_bar)
		returnPage = view.findViewById(R.id.fragment_registration_return_button)
		dateOfBirth = view.findViewById(R.id.fragment_registration_user_birthday_text_view)
		continueRegistrtion = view.findViewById(R.id.fragment_registration_continue)
		profileImageLoader = view.findViewById(R.id.fragment_registration_user_image)
		returnPage.setOnClickListener(this)
		profileImageLoader.setOnClickListener(this)
		genderSelectionText.setOnClickListener(this)
		dateOfBirth.setOnClickListener(this)
		continueRegistrtion.setOnClickListener(this)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
			val selectedImageUri = data.data
			val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

			val cursor = selectedImageUri?.let {
				activity?.contentResolver?.query(
					it,
					filePathColumn,
					null,
					null,
					null
				)
			}
			cursor?.moveToFirst()

			val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
			filePath = columnIndex?.let { cursor.getString(it) }.toString()

			Glide.with(this)
				.asBitmap()
				.load(selectedImageUri)
				.apply(RequestOptions.circleCropTransform())
				.into(profileImageLoader)

			cursor?.close()
		}
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,
											grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				openGallery()
			} else {
				// Разрешение не получено, выполните альтернативные действия
			}
		}
	}

	override fun onClick(view: View?) {
		when (view?.id) {
			genderSelectionText.id -> {
				val dialog = GenderSelectionDialog()
				dialog.setListener(this)
				dialog.show(requireActivity().supportFragmentManager, "GenderSelectionDialog")
			}
			returnPage.id -> {
				requireActivity().supportFragmentManager.popBackStack()
			}
			dateOfBirth.id -> {
				getBirthday()
			}
			continueRegistrtion.id -> {
				val user = User(
					email = fragment_registration_user_email_edit_text.text.toString(),
					password = null,
					name = fragment_registration_user_name_edit_text.text.toString(),
					nickname = fragment_registration_user_nickname_edit_text.text.toString(),
					phone_number = fragment_registration_user_phone_edit_text.text.toString(),
					gender = gender,
					birthday = birthday,
					image = filePath,
					token = ""
				)

				val bundle = Bundle().apply {
					putParcelable("user_regis", user)
				}
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = PasswordGenerationFragment()
				fragment.arguments = bundle
				ft.add(R.id.nav_host_fragment_activity_main, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
			profileImageLoader.id -> {
				if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(requireActivity(),
						arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
						MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
				} else {
					val intent =
						Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
					intent.type = "image/*"
					startActivityForResult(intent, PICK_IMAGE_REQUEST)
				}
			}
		}
	}

	override fun getGender(gender: String) {
		if (gender == "male") {
			genderSelectionText.text = "Мужчина"
		} else {
			genderSelectionText.text = "Женщина"
		}


		this.gender = gender
	}

	private fun initObservers() {
		registrationViewModel.registrationStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun handleState(state: RegistrationState) {
		when (state) {
			is RegistrationState.Failed -> {
				fragment_registration_loader.visibility = View.GONE
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RegistrationState.Loading -> {
				fragment_registration_loader.visibility = View.VISIBLE
			}
			is RegistrationState.Success -> {
				fragment_registration_loader.visibility = View.GONE
				val bundle = Bundle().apply {
					putString("email", fragment_registration_user_email_edit_text.text.toString())
				}
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = VerificationCodeFragment()
				fragment.arguments = bundle
				ft.add(R.id.fragment_container, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}

	private fun getBirthday() {
		val calendar = Calendar.getInstance()
		val year = calendar.get(Calendar.YEAR)
		val month = calendar.get(Calendar.MONTH)
		val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

		val datePickerDialog = DatePickerDialog(
			requireContext(),
			{ view, year, month, dayOfMonth ->
				dateOfBirth.text = "$year-${month + 1}-$dayOfMonth"
				birthday = "$year-${month + 1}-$dayOfMonth"
			}, year, month, dayOfMonth
		)

		datePickerDialog.show()
	}

	private fun openGallery() {
		val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
		startActivityForResult(intent, PICK_IMAGE_REQUEST)
	}
}