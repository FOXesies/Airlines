package com.test.home.domain.repository

import com.test.home.domain.model.Offer
import com.test.home.domain.model.OffersResponse
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface HomeApi {
    @GET("https://drive.usercontent.google.com/u/0/uc?id=1o1nX3uFISrG1gR-jr_03Qlu4_KEZWhav&export=download")
    suspend fun getOffers(): OffersResponse
}