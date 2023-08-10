package com.start.dvizk.create.organization.create.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.create.presentation.model.OrganizationCreatingState
import com.start.dvizk.create.organization.list.presentation.OrganizationListFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

private const val PICK_IMAGE_REQUEST = 1
private const val MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123

class CreateOrgonizationFragment : Fragment() {

	private val createOrganizationViewModel: CreateOrganizationViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	private lateinit var fragment_create_organization_back_image: ImageView
	private lateinit var fragment_create_organization_avatar: ImageView
	private lateinit var fragment_create_organization_name_edit_text: EditText
	private lateinit var fragment_create_organization_edit_text_1: EditText
	private lateinit var fragment_create_organization_phone_number_edit_text: EditText
	private lateinit var fragment_create_organization_email_edit_text: EditText
	private lateinit var fragment_create_organization_whatsapp_edit_text: EditText
	private lateinit var fragment_create_organization_instagram_edit_text: EditText
	private lateinit var fragment_create_organization_back: Button
	private lateinit var fragment_create_organization_next: Button
	private lateinit var progress_bar: View

	var filePath = ""

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_create_organizations, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		initObservers()
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
				.into(fragment_create_organization_avatar)

			cursor?.close()
		}
	}

	private fun initView(view: View) {
		fragment_create_organization_back_image =
			view.findViewById(R.id.fragment_create_organization_back_image)
		fragment_create_organization_avatar =
			view.findViewById(R.id.fragment_create_organization_avatar)
		fragment_create_organization_name_edit_text =
			view.findViewById(R.id.fragment_create_organization_name_edit_text)
		fragment_create_organization_edit_text_1 =
			view.findViewById(R.id.fragment_create_organization_edit_text_1)
		fragment_create_organization_phone_number_edit_text =
			view.findViewById(R.id.fragment_create_organization_phone_number_edit_text)
		fragment_create_organization_email_edit_text =
			view.findViewById(R.id.fragment_create_organization_email_edit_text)
		fragment_create_organization_whatsapp_edit_text =
			view.findViewById(R.id.fragment_create_organization_whatsapp_edit_text)
		fragment_create_organization_instagram_edit_text =
			view.findViewById(R.id.fragment_create_organization_instagram_edit_text)
		fragment_create_organization_back =
			view.findViewById(R.id.fragment_create_organization_back)
		fragment_create_organization_next =
			view.findViewById(R.id.fragment_create_organization_next)
		progress_bar =
			requireActivity().findViewById(R.id.progress_bar)

		fragment_create_organization_back_image.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		fragment_create_organization_back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		fragment_create_organization_next.setOnClickListener {
			createOrganization()
		}
		fragment_create_organization_avatar.setOnClickListener {
			if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
				!= PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(requireActivity(),
					arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
					MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
				)
			} else {
				val intent =
					Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
				intent.type = "image/*"
				startActivityForResult(intent,
					PICK_IMAGE_REQUEST
				)
			}
		}
	}

	private fun createOrganization() {
		createOrganizationViewModel.createOrganization(
			token = sharedPreferencesRepository.getUserToken(),
			name = fragment_create_organization_name_edit_text.text.toString(),
			description = fragment_create_organization_edit_text_1.text.toString(),
			phone_number = fragment_create_organization_phone_number_edit_text.text.toString(),
			instagram = fragment_create_organization_instagram_edit_text.text.toString(),
			whatsapp = fragment_create_organization_whatsapp_edit_text.text.toString(),
			email = fragment_create_organization_email_edit_text.text.toString(),
			image = getMultipart(filePath),
		)
	}

	private fun initObservers() {
		createOrganizationViewModel.organizationCreatingState.observe(viewLifecycleOwner, ::handle)
	}

	private fun handle(value: OrganizationCreatingState) {
		when (value) {
			is OrganizationCreatingState.Success -> {
				progress_bar.visibility = View.GONE
				Toast.makeText(requireContext(), value.message, Toast.LENGTH_LONG).show()
				requireActivity().supportFragmentManager.popBackStack()
			}
			is OrganizationCreatingState.Failed -> {
				progress_bar.visibility = View.GONE
				Toast.makeText(requireContext(), value.message, Toast.LENGTH_LONG).show()
			}
			is OrganizationCreatingState.Loading -> {
				progress_bar.visibility = View.VISIBLE
			}
		}
	}

	private fun openGallery() {
		val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
		startActivityForResult(intent, PICK_IMAGE_REQUEST)
	}

	private fun getMultipart(filePath: String?): MultipartBody.Part? {
		if (filePath.isNullOrEmpty()) {

			return null
		}
		val file = File(filePath)

		val body = RequestBody.create("image/*".toMediaTypeOrNull(), file)

		return MultipartBody.Part.createFormData("image", file.name, body)
	}
}