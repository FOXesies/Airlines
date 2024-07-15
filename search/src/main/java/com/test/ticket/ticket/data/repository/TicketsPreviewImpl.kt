package com.test.ticket.ticket.data.repository

import com.test.ticket.ticket.domain.repository.TicketsPreviewApi
import javax.inject.Singleton

@Singleton
class TicketsPreviewImpl(val ticketsPreviewApi: TicketsPreviewApi) {
    suspend fun getTickets() = ticketsPreviewApi.getTickets()
}