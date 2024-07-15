package com.test.ticket.ticket.presentation

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.core_navigation.MainModelView
import com.test.core_navigation.util.UiMainEvent
import com.test.ticket.databinding.TicketFragmentBinding
import com.test.ticket.ticket.presentation.adapter.TicketsPreviewAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class TicketsFragment: Fragment() {
    private var binding_: TicketFragmentBinding? = null
    private val binding get() = binding_!!

    private lateinit var modelView: TicketsPreviewModelView
    private lateinit var mainModelView: MainModelView
    private lateinit var adapter: TicketsPreviewAdapter
    private lateinit var calendarPicker: DatePickerDialog
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

        binding.dateRoute.setOnClickListener {
            calendarPicker.show()
        }

        binding.backImage.setOnClickListener {
            mainModelView.onBack()
        }

        binding.openTickets.setOnClickListener {
            mainModelView.setState(UiMainEvent.OpenTicketList)
        }

        initObserver()
    }

    private fun initObserver(){
        modelView.tickets.observe(viewLifecycleOwner){
            it?.let { list ->
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

    private fun setDateInfo(calendar: Calendar){
        val dateFormat = SimpleDateFormat("dd MMM, EEE", Locale("ru"))
        val formattedDate = dateFormat.format(calendar.time)
        binding.dateDayTv.text = formattedDate.split(", ")[0].replace(".", "")
        binding.dateNameTv.text = ", ".plus(formattedDate.split(", ")[1])
    }

}