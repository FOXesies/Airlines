package com.test.ticket.ticket.domain.model

import com.test.ticket.domain.model.TicketPreview

data class ResponseTicketPreview(
    val tickets_offers: List<TicketPreview>
)