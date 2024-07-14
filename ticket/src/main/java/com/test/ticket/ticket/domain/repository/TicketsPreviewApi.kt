package com.test.ticket.ticket.domain.repository

import com.test.ticket.ticket.domain.model.ResponseTicketPreview
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TicketsPreviewApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download")
    suspend fun getTickets(): ResponseTicketPreview
}