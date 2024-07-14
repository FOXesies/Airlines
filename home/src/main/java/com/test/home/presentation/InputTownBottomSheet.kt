package com.test.home.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.core_navigation.MainModelView
import com.test.core_navigation.util.UiMainEvent
import com.test.home.databinding.InputTownDialogFragmentBinding
import com.test.home.domain.model.Suggest
import com.test.home.domain.model.TypeSuggest
import com.test.home.presentation.adapter.SuggestAdapter
import com.test.home.util.UiEventHome
import com.test.home.util.getFromSuggest
import com.test.home.util.getToSuggest
import com.test.home.util.saveFromSuggest
import com.test.home.util.saveToSuggest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InputTownBottomSheet: BottomSheetDialogFragment(), OnSuggestClickListener {
    private var binding_: InputTownDialogFragmentBinding? = null

    private var modelView_: HomeModelView? = null

    private lateinit var mainModelView: MainModelView
    private val modelView get() = modelView_!!
    private val binding get() = binding_!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelView_ = ViewModelProvider(requireActivity())[HomeModelView::class]
        mainModelView = ViewModelProvider(requireActivity())[MainModelView::class]
        binding_ = InputTownDialogFragmentBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val displayMetrics = resources.displayMetrics
        val height = displayMetrics.heightPixels
        binding.containerFragment.layoutParams.height = (height / 1.025).toInt()
    }

    private fun init(){
        val list = listOf(
            Suggest(1, "Стамбул", "Популярное направление"),
            Suggest(2, "Сочи", "Популярное направление"),
            Suggest(3, "Пхукет", "Популярное направление")
        )

        binding.suggestTown.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val suggestAdapter = SuggestAdapter(this)
        suggestAdapter.suggests = list
        binding.suggestTown.adapter = suggestAdapter

        binding.formEt.logicTextChanged(binding.clearFromImage, binding.replaceToImage, binding.toEt)
        binding.toEt.logicTextChanged(binding.clearToImage, binding.replaceFromImage, binding.formEt)

        binding.formEt.savebleLogic(TypeSuggest.FROM_TOWN)
        binding.toEt.savebleLogic(TypeSuggest.TO_TOWN)

        binding.randomTown.setOnClickListener {
            val pick = list.random().title
            binding.toEt.setText(pick)
            binding.toEt.requestFocus()

        }
    }

    private fun EditText.savebleLogic(type: TypeSuggest){
        when(type){
            TypeSuggest.TO_TOWN -> lifecycleScope.launch {
                getToSuggest(requireContext())?.let {
                    hint = hint.toString().plus(" - ").plus(it)
                }
            }
            TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                getFromSuggest(requireContext())?.let {
                    hint = hint.toString().plus(" - ").plus(it)
                }
            }
        }

        setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if(text.isNullOrEmpty()) {
                    Toast.makeText(requireActivity(), "Поле пустое", Toast.LENGTH_SHORT).show()
                    return@setOnEditorActionListener false
                }

                when(type){
                    TypeSuggest.TO_TOWN -> lifecycleScope.launch {
                        saveToSuggest(requireContext())
                        modelView.onEvent(UiEventHome.SaveTownTo(text.toString()))
                    }
                    TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                        saveFromSuggest(requireContext())
                        modelView.onEvent(UiEventHome.SaveTownFrom(text.toString()))
                    }
                }

                lifecycleScope.launch {
                    this@savebleLogic.getToSuggest(requireContext())?.let {
                        this@savebleLogic.getFromSuggest(requireContext())?.let {
                            mainModelView.setState(UiMainEvent.OpenTicketPreview)
                        }
                    }
                }

                true
            } else {
                false
            }
        }
    }

    private fun EditText.logicTextChanged(close: RelativeLayout, replace: RelativeLayout, secondText: EditText) {
        // Очистка текста
        close.setOnClickListener {
            this.setText("")
        }

        // Замена текста между EditText
        replace.setOnClickListener {
            val secondTextValue = secondText.text.toString()
            secondText.setText(this.text.toString())
            this.setText(secondTextValue)

            secondText.requestFocus()
        }

        onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                if (text.isNotEmpty()) {
                    replace.visibility = View.VISIBLE
                    close.visibility = View.VISIBLE
                } else {
                    replace.visibility = View.GONE
                    close.visibility = View.VISIBLE
                }
            } else {
                replace.visibility = View.GONE
                close.visibility = View.GONE
            }
        }

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    close.visibility = View.GONE
                    replace.visibility = View.GONE
                } else {
                    close.visibility = View.VISIBLE
                    
                    if (secondText.text.isNullOrEmpty()) {
                        replace.visibility = View.GONE
                    } else {
                        replace.visibility = View.VISIBLE
                    }
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        if (dialog != null) {
            dialog!!.window!!
                .setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onSuggestClick(suggest: String) {
        binding.toEt.setText(suggest)
        binding.toEt.requestFocus()
    }
}

interface OnSuggestClickListener {
    fun onSuggestClick(suggest: String)
}