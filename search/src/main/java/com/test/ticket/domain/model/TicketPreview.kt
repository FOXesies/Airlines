package com.test.ticket.domain.model

import com.test.model.Price

data class TicketPreview(
    val id: Long,
    val title: String,
    val time_range: List<String>,
    val price: Price
)