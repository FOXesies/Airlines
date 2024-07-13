package com.test.home.domain.model

data class Offer(
    val id: Long,
    val title: String,
    val town: String,
    val price: Price
)