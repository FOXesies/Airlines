package com.test.home.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.test.home.databinding.InputTownDialogFragmentBinding
import com.test.home.domain.model.Suggest
import com.test.input_town.domain.model.TypeSuggest
import com.test.util.getFromSuggest
import com.test.util.getToSuggest
import com.test.util.saveFromSuggest
import com.test.util.saveToSuggest
import kotlinx.coroutines.launch

class InputTownBottomSheet: BottomSheetDialogFragment() {
    private var binding_: InputTownDialogFragmentBinding? = null
    private val binding get() = binding_!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        val suggestAdapter = SuggestAdapter()
        suggestAdapter.suggests = list
        binding.suggestTown.adapter = suggestAdapter

        binding.formEt.logicTextChanged(binding.clearFromImage, binding.replaceToImage, binding.toEt)
        binding.toEt.logicTextChanged(binding.clearToImage, binding.replaceFromImage, binding.formEt)

        binding.formEt.savebleLogic(TypeSuggest.FROM_TOWN)
        binding.toEt.savebleLogic(TypeSuggest.TO_TOWN)
    }

    private fun EditText.savebleLogic(type: TypeSuggest){
        when(type){
            TypeSuggest.TO_TOWN -> lifecycleScope.launch {
                hint = hint.toString().plus(getToSuggest(requireContext()))
            }
            TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                hint = hint.toString().plus(getFromSuggest(requireContext()))
            }
        }

        setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER) && event.action == KeyEvent.ACTION_UP) {

                if(text.isNullOrEmpty()){
                    Toast.makeText(requireActivity(), "Поле пустое", Toast.LENGTH_SHORT).show()
                    return@OnKeyListener false
                }

                when(type){
                    TypeSuggest.TO_TOWN -> lifecycleScope.launch { saveToSuggest(requireContext()) }
                    TypeSuggest.FROM_TOWN -> lifecycleScope.launch { saveFromSuggest(requireContext()) }
                }

                return@OnKeyListener true
            }
            false
        })
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
}
