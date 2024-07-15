package com.test.tickets.domain.model

import com.test.model.Price

data class Ticket(
    val id: Long,
    val badge: String? = null,
    val price: Price,
    val provider_name: String,
    val company: String,
    val departure: InfoRoute,
    val arrival: InfoRoute,
    val has_transfer: Boolean,
    val has_visa_transfer: Boolean,
    val luggage: Luggage,
    val hand_luggage: HandLuggage,
    val is_returnable: Boolean,
    val is_exchangable: Boolean,
)
