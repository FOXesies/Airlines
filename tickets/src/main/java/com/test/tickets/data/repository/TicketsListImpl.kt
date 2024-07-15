package com.test.tickets.data.repository

import com.test.tickets.domain.repository.TicketsListApi
import javax.inject.Singleton

@Singleton
class TicketsListImpl(val ticketsListApi: TicketsListApi) {
    suspend fun getTickets() = ticketsListApi.getTickets()
}