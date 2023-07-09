package com.start.dvizk.create.steps.photo

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.photo.model.ImageAdapter
import com.start.dvizk.create.steps.photo.model.PhotoResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

private const val REQUEST_PERMISSION_CODE = 1
private const val REQUEST_GALLERY = 2
private const val PICK_IMAGES_REQUEST_CODE = 3

class PhotoStepFragment : Fragment() {

	private val viewModel: PhotoStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()


	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var photo: ImageView
	private lateinit var photoAdditional: ImageView
	private lateinit var item_event_image: ImageView

	private lateinit var mainImage: PhotoResponse

	private lateinit var recyclerView: RecyclerView
	private lateinit var imageAdapter: ImageAdapter
	private val images: MutableList<PhotoResponse> = mutableListOf()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_photo_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.requestMainImageAddResponseStateLiveData.observe(viewLifecycleOwner, ::handleMainImageState)
		viewModel.requestAdditionalImageAddResponseStateLiveData.observe(viewLifecycleOwner, ::handleAdditionalImageState)
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)

	}

	override fun onRequestPermissionsResult(
		requestCode: Int,
		permissions: Array<out String>,
		grantResults: IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)

		if (requestCode == REQUEST_PERMISSION_CODE) {
			if (isPermissionGranted()) {
				// Разрешения предоставлены
			} else {
				// Разрешения не предоставлены, обработайте это соответствующим образом
			}
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
			val imageUri = data?.data
			imageUri?.let {
				val file = File(getRealPathFromUri(requireContext(), imageUri))
				val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
				val photoPart = MultipartBody.Part.createFormData("image", file.name, requestFile)

				arguments?.apply {
					viewModel.sendEventMainImage(
						authorization = sharedPreferencesRepository.getUserToken(),
						image = photoPart,
						eventId = getInt(EVENT_ID_KEY),
					)
				}
			}
		}

		if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			val selectedImages = mutableListOf<Uri>()
			val clipData = data?.clipData
			if (clipData != null) {
				// Если выбрано несколько фотографий
				for (i in 0 until clipData.itemCount) {
					val imageUri = clipData.getItemAt(i).uri
					selectedImages.add(imageUri)
				}
			} else {
				// Если выбрана только одна фотография
				val imageUri = data?.data
				if (imageUri != null) {
					selectedImages.add(imageUri)
				}
			}

			for (imageUri in selectedImages) {
				val file = File(getRealPathFromUri(requireContext(), imageUri))
				val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
				val photoPart = MultipartBody.Part.createFormData("image", file.name, requestFile)

				arguments?.apply {
					viewModel.sendEventAdditionalImage(
						authorization = sharedPreferencesRepository.getUserToken(),
						image = photoPart,
						eventId = getInt(EVENT_ID_KEY),
					)
				}
			}

		}
	}

	private fun getRealPathFromUri(context: Context, uri: Uri): String? {
		var filePath: String? = null
		if (ContentResolver.SCHEME_CONTENT == uri.scheme) {
			var inputStream: InputStream? = null
			var outputStream: FileOutputStream? = null
			try {
				val tempFile = File.createTempFile("temp_", ".jpg", context.cacheDir)
				tempFile.deleteOnExit()

				inputStream = context.contentResolver.openInputStream(uri)
				outputStream = FileOutputStream(tempFile)

				val buffer = ByteArray(4 * 1024)
				var bytesRead: Int
				while (inputStream?.read(buffer).also { bytesRead = it ?: 0 } != -1) {
					outputStream.write(buffer, 0, bytesRead)
				}

				filePath = tempFile.absolutePath
			} catch (e: IOException) {
				e.printStackTrace()
			} finally {
				inputStream?.close()
				outputStream?.close()
			}
		} else if (ContentResolver.SCHEME_FILE == uri.scheme) {
			filePath = uri.path
		}

		return filePath
	}

	private fun initView(view: View) {
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		photo = view.findViewById(R.id.fragment_create_organization_title_8)
		photoAdditional = view.findViewById(R.id.fragment_create_organization_title_10)
		item_event_image = view.findViewById(R.id.item_event_image)
		recyclerView = view.findViewById(R.id.additional_photo)

		val spanCount = calculateSpanCount() // Определение количества столбцов

		val layoutManager = GridLayoutManager(requireContext(), spanCount, LinearLayoutManager.VERTICAL, false)
		recyclerView.layoutManager = layoutManager

		imageAdapter = ImageAdapter()
		recyclerView.adapter = imageAdapter

		photo.setOnClickListener {
			if (isPermissionGranted()) {
				openGalleryForMainImage()
			} else {
				requestPermissions()
			}
		}

		photoAdditional.setOnClickListener {
			if (isPermissionGranted()) {
				openGalleryForMultiChoice()
			} else {
				requestPermissions()
			}
		}

		next.setOnClickListener {
			arguments?.apply {
				viewModel.sendEventImage(
					authorization = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY)
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
	}

	private fun requestPermissions() {
		val permissions = arrayOf(
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE
		)

		if (isPermissionGranted()) {
			Toast.makeText(requireContext(), "Попробуйте еще раз", Toast.LENGTH_LONG).show()

			return
		}

		ActivityCompat.requestPermissions(requireActivity(), permissions, REQUEST_PERMISSION_CODE)
	}

	private fun isPermissionGranted(): Boolean {
		val readPermission = ContextCompat.checkSelfPermission(
			requireContext(),
			Manifest.permission.READ_EXTERNAL_STORAGE
		)
		val writePermission = ContextCompat.checkSelfPermission(
			requireContext(),
			Manifest.permission.WRITE_EXTERNAL_STORAGE
		)
		return readPermission == PackageManager.PERMISSION_GRANTED && writePermission == PackageManager.PERMISSION_GRANTED
	}

	private fun openGalleryForMainImage() {
		val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
		startActivityForResult(intent, REQUEST_GALLERY)
	}

	private fun openGalleryForMultiChoice() {
		val intent = Intent(Intent.ACTION_PICK)
		intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
		intent.type = "image/*"
		startActivityForResult(intent, PICK_IMAGES_REQUEST_CODE)
	}

	private fun handleMainImageState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? PhotoResponse ?: return responseFailed()
				mainImage = response
				Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
				item_event_image.visibility = View.VISIBLE
				Glide.with(this)
					.load(response.data.image)
					.into(item_event_image)
			}
		}
	}

	private fun handleAdditionalImageState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? PhotoResponse ?: return responseFailed()
				Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG).show()
				images.add(response)
				imageAdapter.setImages(images)
			}
		}
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG).show()
	}

	private fun calculateSpanCount(): Int {
		val displayMetrics = resources.displayMetrics
		val screenWidth = displayMetrics.widthPixels
		val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width) // Ширина элемента списка

		return screenWidth / itemWidth
	}

	private fun handleState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}
			is RequestResponseState.Loading -> {

			}
			is RequestResponseState.Success -> {
				val response = state.value as? StepDataApiResponse ?: return responseFailed()

				val ft: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(response.data.nextStep.name)
				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, response.data.nextStep.numberStep)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container,fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}
}