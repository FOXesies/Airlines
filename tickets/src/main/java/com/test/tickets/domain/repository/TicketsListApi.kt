package com.test.tickets.domain.repository

import com.test.tickets.domain.model.ResponseTicket
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TicketsListApi {
    @GET("https://drive.google.com/uc?export=download&id=1HogOsz4hWkRwco4kud3isZHFQLUAwNBA")
    suspend fun getTickets(): ResponseTicket
}