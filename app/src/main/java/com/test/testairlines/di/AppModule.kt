package com.test.testairlines.di

import com.test.home.data.repository.HomeApiImpl
import com.test.home.domain.repository.HomeApi
import com.test.ticket.ticket.data.repository.TicketsPreviewImpl
import com.test.ticket.ticket.domain.repository.TicketsPreviewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val GOOGLE_URL = "https://drive.usercontent.google.com/u/0/uc?id=1"

    @Singleton
    @Provides
    fun provideHttpsLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideHomeApi(retrofit: Retrofit): HomeApi = retrofit.create(HomeApi::class.java)

    @Singleton
    @Provides
    fun provideHomeImpl(homeApi: HomeApi) = HomeApiImpl(homeApi)

    @Singleton
    @Provides
    fun provideTicketsPreviewApi(retrofit: Retrofit): TicketsPreviewApi = retrofit.create(TicketsPreviewApi::class.java)

    @Singleton
    @Provides
    fun provideTicketsPreviewImpl(ticketApi: TicketsPreviewApi) = TicketsPreviewImpl(ticketApi)
}