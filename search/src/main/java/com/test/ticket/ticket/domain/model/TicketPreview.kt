package com.test.ticket.ticket.domain.model

data class TicketPreview(
    val id: Long,
    val title: String,
    val time_range: List<String>,
    val price: Price
)