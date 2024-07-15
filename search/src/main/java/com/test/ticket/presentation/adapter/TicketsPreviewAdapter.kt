package com.test.ticket.ticket.presentation.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.test.ticket.domain.model.TicketPreview
import com.test.ticket.ticket.util.ticketPreviewDelegate

class TicketsPreviewAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val delegatesManager = AdapterDelegatesManager<List<TicketPreview>>()
        .addDelegate(ticketPreviewDelegate())

    var tickets = emptyList<TicketPreview>()
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