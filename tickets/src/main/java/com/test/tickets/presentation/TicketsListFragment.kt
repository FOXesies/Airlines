package com.test.tickets.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.core_navigation.MainModelView
import com.test.tickets.databinding.TicketListFragmentBinding
import com.test.tickets.presentation.adapter.TicketListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TicketsListFragment: Fragment() {
    private var binding_: TicketListFragmentBinding? = null
    private val binding get() = binding_!!
    private lateinit var modelView: TicketsModelView
    private lateinit var mainModelView: MainModelView
    private lateinit var adapter: TicketListAdapter
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

    private fun init(){
        binding.tickets.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = TicketListAdapter()
        binding.tickets.adapter = adapter

        initObserver()
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