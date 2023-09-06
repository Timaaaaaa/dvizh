package com.start.dvizk.create.steps.language

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.start.dvizk.R
import com.start.dvizk.arch.EventCreateRouter
import com.start.dvizk.arch.data.SharedPreferencesRepository
import com.start.dvizk.create.organization.list.presentation.EVENT_ID_KEY
import com.start.dvizk.create.organization.list.presentation.STEP_NUMBER_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.BottomSheetSelectListFragment
import com.start.dvizk.create.steps.bottomsheet.universal.IS_MULTI_SELECT_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.OnBottomSheetDismissListener
import com.start.dvizk.create.steps.bottomsheet.universal.SELECT_LIST_KEY
import com.start.dvizk.create.steps.bottomsheet.universal.model.SelectItem
import com.start.dvizk.create.steps.data.model.RequestResponseState
import com.start.dvizk.create.steps.data.model.StepDataApiResponse
import com.start.dvizk.create.steps.language.model.EventParameter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LanguageStepFragment : Fragment(), OnBottomSheetDismissListener {

	private val viewModel: LanguageStepViewModel by viewModel()
	private val sharedPreferencesRepository: SharedPreferencesRepository by inject()

	var languages = listOf<EventParameter>()

	private lateinit var next: Button
	private lateinit var back: Button
	private lateinit var languagesText: TextView

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(R.layout.fragment_language_step, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initView(view)
		viewModel.requestResponseStateLiveData.observe(viewLifecycleOwner, ::handleLanguageState)
		viewModel.requestSendLangResponseStateLiveData.observe(viewLifecycleOwner, ::handleState)
	}

	private fun initView(view: View) {
		val headerBack: ImageView = view.findViewById(R.id.fragment_create_organization_back_image)
		headerBack.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}
		next = view.findViewById(R.id.fragment_create_organization_next)
		back = view.findViewById(R.id.fragment_create_organization_back)
		languagesText = view.findViewById(R.id.fragment_language_step_languages_text)

		next.setOnClickListener {
			var ids = mutableListOf<Int>()
			languages.forEach { lang ->
				if (lang.isSelected) {
					ids.add(lang.id)
				}
			}
			arguments?.apply {
				viewModel.sendEventLanguages(
					token = sharedPreferencesRepository.getUserToken(),
					numberStep = getInt(STEP_NUMBER_KEY),
					eventId = getInt(EVENT_ID_KEY),
					languages = ids
				)
			}
		}

		back.setOnClickListener {
			requireActivity().supportFragmentManager.popBackStack()
		}

		languagesText.setOnClickListener {
			if (languages.isEmpty()) {
				viewModel.getLanguages()
			} else {
				var selectListCurrent = listOf<SelectItem>()
				languages.forEach {
					selectListCurrent =
						selectListCurrent.plus(mapToSelectListItem(it.id, it.name, it.isSelected))
				}
				val bottomSheetFragment = BottomSheetSelectListFragment()
				bottomSheetFragment.setListener(this)
				val args = Bundle()
				args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(selectListCurrent))
				args.putBoolean(IS_MULTI_SELECT_KEY, true)
				args.putString("TITLE", "Выберите языки")
				bottomSheetFragment.arguments = args
				bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
			}
		}
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
				val imm: InputMethodManager =
					context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
				imm.hideSoftInputFromWindow(view?.windowToken, 0)
				val ft: FragmentTransaction =
					requireActivity().supportFragmentManager.beginTransaction()
				val fragment = EventCreateRouter.getCreateStepFragment(response.data.nextStep.name)

				fragment.arguments = Bundle().apply {
					putInt(STEP_NUMBER_KEY, response.data.nextStep.numberStep)
					putInt(EVENT_ID_KEY, response.data.eventId)
				}
				ft.add(R.id.fragment_container, fragment)
				ft.addToBackStack(null)
				ft.commit()
			}
		}
	}

	private fun handleLanguageState(state: RequestResponseState) {
		when (state) {
			is RequestResponseState.Failed -> {
				Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
			}

			is RequestResponseState.Loading -> {

			}

			is RequestResponseState.Success -> {
				languages = state.value as? List<EventParameter> ?: return responseFailed()
				var selectListCurrent = listOf<SelectItem>()
				languages.forEach {
					selectListCurrent =
						selectListCurrent.plus(mapToSelectListItem(it.id, it.name, it.isSelected))
				}
				val bottomSheetFragment = BottomSheetSelectListFragment()
				bottomSheetFragment.setListener(this)

				val args = Bundle()
				args.putParcelableArrayList(SELECT_LIST_KEY, ArrayList(selectListCurrent))
				args.putBoolean(IS_MULTI_SELECT_KEY, true)
				args.putString("TITLE", "Выберите языки")
				bottomSheetFragment.arguments = args
				bottomSheetFragment.show(parentFragmentManager, "MyBottomSheetFragmentTag")
			}
		}
	}

	fun mapToSelectListItem(id: Int, name: String, isSelected: Boolean): SelectItem {
		return SelectItem(id = id, name = name, isSelect = isSelected)
	}

	private fun responseFailed() {
		Toast.makeText(requireContext(), "Ошибка сервера попробуйте позже", Toast.LENGTH_LONG)
			.show()
	}

	override fun onBottomSheetDismiss(
		ids: List<Int>,
		parameterName: String,
		list: MutableList<SelectItem>
	) {
		var languagesNames = ""
		var secondLang = false
		languages.forEach { lang ->
			lang.isSelected = false
			ids.forEach {
				if(lang.id == it) {

					lang.isSelected = true
					if (secondLang) {
						languagesNames += ", " + lang.name
					} else {
						languagesNames += lang.name
					}

					secondLang = true
				}
			}
		}

		languagesText.text = languagesNames
	}
}