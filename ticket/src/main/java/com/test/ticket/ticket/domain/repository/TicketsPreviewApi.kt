package com.test.ticket.ticket.domain.repository

import com.test.ticket.ticket.domain.model.ResponseTicketPreview
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TicketsPreviewApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=13WhZ5ahHBwMiHRXxWPq-bYlRVRwAujta&export=download")
    suspend fun getTickets(): ResponseTicketPreview
}