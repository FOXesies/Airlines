package com.test.ticket.ticket.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.ticket.databinding.TicketFragmentBinding
import com.test.ticket.ticket.presentation.adapter.TicketsPreviewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsFragment: Fragment() {
    private var binding_: TicketFragmentBinding? = null
    private val binding get() = binding_!!

    private lateinit var modelView: TicketsPreviewModelView
    private lateinit var adapter: TicketsPreviewAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding_ = TicketFragmentBinding.inflate(inflater, container, false)
        modelView = ViewModelProvider(this)[TicketsPreviewModelView::class]
        init()

        return binding.root
    }

    private fun init(){
        adapter = TicketsPreviewAdapter()
        binding.ticketsSuggest.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.ticketsSuggest.adapter = adapter

        initObserver()
    }

    private fun initObserver(){
        modelView.tickets.observe(this){
            it?.let {
                adapter.tickets = it.response
                adapter.notifyDataSetChanged()
            }
        }
    }

}