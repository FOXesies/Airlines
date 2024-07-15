package com.test.ticket.ticket.presentation

import android.app.AlertDialog
import android.app.DatePickerDialog
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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.core_navigation.MainModelView
import com.test.core_navigation.util.UiMainEvent
import com.test.model.TypeSuggest
import com.test.ticket.R
import com.test.ticket.databinding.TicketFragmentBinding
import com.test.ticket.ticket.presentation.adapter.TicketsPreviewAdapter
import com.test.utils.getFromSuggest
import com.test.utils.getToSuggest
import com.test.utils.saveFromSuggest
import com.test.utils.saveToDate
import com.test.utils.saveToSuggest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class TicketsFragment: Fragment() {
    private var binding_: TicketFragmentBinding? = null
    private val binding get() = binding_!!
    private var isRevers = false

    private lateinit var modelView: TicketsPreviewModelView
    private lateinit var mainModelView: MainModelView
    private lateinit var adapter: TicketsPreviewAdapter
    private lateinit var calendarPicker: DatePickerDialog
    private lateinit var calendarPickerRevers: DatePickerDialog

    private val baseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding_ = TicketFragmentBinding.inflate(inflater, container, false)
        modelView = ViewModelProvider(this)[TicketsPreviewModelView::class]
        mainModelView = ViewModelProvider(requireActivity())[MainModelView::class]
        init()

        return binding.root
    }

    private fun init(){
        adapter = TicketsPreviewAdapter()
        binding.ticketsSuggest.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ticketsSuggest.adapter = adapter

        createDatePicker()
        createDateReversePicker()

        binding.dateRoute.setOnClickListener {
            calendarPicker.show()
        }

        binding.cardReverse.setOnClickListener {
            if (isRevers){
                showReverseDialog()
                return@setOnClickListener
            }
            calendarPickerRevers.show()
        }

        binding.backImage.setOnClickListener {
            mainModelView.onBack()
        }

        binding.openTickets.setOnClickListener {
            mainModelView.setState(UiMainEvent.OpenTicketList)
        }

        binding.formEt.getSaveValue(TypeSuggest.FROM_TOWN)
        binding.toEt.getSaveValue(TypeSuggest.TO_TOWN)

        binding.formEt.logicTextChanged(binding.clearFromImage, binding.replaceToImage, binding.toEt)
        binding.toEt.logicTextChanged(binding.clearToImage, binding.replaceFromImage, binding.formEt)

        binding.formEt.savebleLogic(TypeSuggest.FROM_TOWN)
        binding.toEt.savebleLogic(TypeSuggest.TO_TOWN)

        initObserver()
    }

    private fun EditText.getSaveValue(type: TypeSuggest){
        when(type){
            TypeSuggest.TO_TOWN -> lifecycleScope.launch {
                getToSuggest(requireContext())?.let {
                    setText(it)
                }
            }
            TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                getFromSuggest(requireContext())?.let {
                    setText(it)
                }
            }
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
                    }
                    TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                        saveFromSuggest(requireContext())
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

    private fun initObserver(){
        modelView.tickets.observe(viewLifecycleOwner){
            it?.let { list ->
                binding.waitLoadSuggest.visibility = View.GONE
                binding.ticketsSuggest.visibility = View.VISIBLE
                adapter.tickets = list.tickets_offers
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun createDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        setDateInfo(c)

        calendarPicker = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->
            val curCalendar = Calendar.getInstance()
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, monthOfYear, dayOfMonth)

            if (selectedCalendar.before(curCalendar)) {
                Toast.makeText(
                    requireActivity(),
                    "Дата не может быть меньше текущей",
                    Toast.LENGTH_SHORT
                ).show()
                return@DatePickerDialog
            }

            setDateInfo(selectedCalendar)

        }, year, month, day)
    }

    private fun createDateReversePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        setDateInfo(c)

        calendarPickerRevers = DatePickerDialog(requireActivity(), { view, year, monthOfYear, dayOfMonth ->
            val curCalendar = Calendar.getInstance()
            val selectedCalendar = Calendar.getInstance()
            selectedCalendar.set(year, monthOfYear, dayOfMonth)

            if (selectedCalendar.before(curCalendar)) {
                Toast.makeText(
                    requireActivity(),
                    "Дата не может быть меньше текущей",
                    Toast.LENGTH_SHORT
                ).show()
                return@DatePickerDialog
            }

            setDateInfoReverse(selectedCalendar)
            isRevers = true

        }, year, month, day)
    }

    private fun removeReverse() {
        isRevers = false
        binding.dateDayTvReverse.text = getString(R.string.ticket_fragment_backward)
        binding.dateNameTvReverse.text = ""
    }

    private fun showReverseDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.que))
            .setPositiveButton("Заменить") { dialog, id ->
                calendarPickerRevers.show()
            }
            .setNegativeButton("Удалить") { dialog, id ->
                removeReverse()
            }

        val dialog = builder.create()
        dialog.show()
    }

    private fun setDateInfo(calendar: Calendar){

        lifecycleScope.launch {
            requireContext().saveToDate(baseFormat.format(calendar.time))
        }

        val dateFormat = SimpleDateFormat("dd MMM, EEE", Locale("ru"))
        val formattedDate = dateFormat.format(calendar.time)
        binding.dateDayTv.text = formattedDate.split(", ")[0].replace(".", "")
        binding.dateNameTv.text = ", ".plus(formattedDate.split(", ")[1])
    }

    private fun setDateInfoReverse(calendar: Calendar){
        val dateFormat = SimpleDateFormat("dd MMM, EEE", Locale("ru"))
        val formattedDate = dateFormat.format(calendar.time)
        binding.dateDayTvReverse.text = formattedDate.split(", ")[0].replace(".", "")
        binding.dateNameTvReverse.text = ", ".plus(formattedDate.split(", ")[1])
    }

}