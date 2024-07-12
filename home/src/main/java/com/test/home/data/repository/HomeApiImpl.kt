package com.test.home.data.repository

import com.test.home.domain.repository.HomeApi
import javax.inject.Singleton

@Singleton
class HomeApiImpl(private var repository: HomeApi) {
    suspend fun getOffers() = repository.getOffers()
}