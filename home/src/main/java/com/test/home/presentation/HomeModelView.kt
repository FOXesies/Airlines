package com.test.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.home.data.repository.HomeApiImpl
import com.test.home.domain.model.Offer
import com.test.home.domain.model.OffersResponse
import com.test.home.domain.repository.HomeApi
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeModelView @Inject constructor(
    private val homeRepository: HomeApi
): ViewModel() {

    private val offers_ = MutableLiveData<OffersResponse>(null)
    val offers: LiveData<OffersResponse> = offers_

    init {
        viewModelScope.launch {
            offers_.postValue(homeRepository.getOffers())
        }
    }

}