package com.test.home.domain.model

import com.test.model.Price

data class Offer(
    val id: Long,
    val title: String,
    val town: String,
    val price: Price
)