package com.test.tickets.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.core_navigation.MainModelView
import com.test.tickets.R
import com.test.tickets.databinding.TicketListFragmentBinding
import com.test.tickets.presentation.adapter.TicketListAdapter
import com.test.utils.getFromSuggest
import com.test.utils.getToDate
import com.test.utils.getToSuggest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class TicketsListFragment: Fragment() {
    private var binding_: TicketListFragmentBinding? = null
    private val binding get() = binding_!!
    private lateinit var modelView: TicketsModelView
    private lateinit var mainModelView: MainModelView
    private lateinit var adapter: TicketListAdapter

    private val baseFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("dd MMM, EEE", Locale("ru"))
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        modelView = ViewModelProvider(this)[TicketsModelView::class]
        binding_ = TicketListFragmentBinding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    private fun init() {
        binding.tickets.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = TicketListAdapter()
        binding.tickets.adapter = adapter
        binding.towns.getSaveValue()

        initObserver()
    }

    private fun TextView.getSaveValue() {
        lifecycleScope.launch {
            getToSuggest(requireContext())?.let { toText ->
                getFromSuggest(requireContext())?.let { fromText ->
                    text = "$fromText—$toText"
                }
            }

            val startDate = baseFormat.parse(getToDate(requireContext()))
            val formattedDate = dateFormat.format(startDate.time)
            binding.infoRoute.text = formattedDate.plus(getString(R.string.hard_one_pass))
        }

    }

    private fun initObserver(){
        modelView.tickets.observe(viewLifecycleOwner){
            it?.let {
                binding.waitLoadTickets.visibility = View.GONE
                binding.tickets.visibility = View.VISIBLE
                adapter.tickets = it.tickets
                adapter.notifyDataSetChanged()
            }
        }
    }

}