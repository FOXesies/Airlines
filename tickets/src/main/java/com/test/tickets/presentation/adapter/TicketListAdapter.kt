package com.test.tickets.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.test.tickets.domain.model.Ticket
import com.test.tickets.util.ticketListDelegate

class TicketListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val delegatesManager = AdapterDelegatesManager<List<Ticket>>()
            .addDelegate(ticketListDelegate())

    var tickets = emptyList<Ticket>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(tickets, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(tickets, position)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }
}