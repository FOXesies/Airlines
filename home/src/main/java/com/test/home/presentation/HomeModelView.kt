package com.test.home.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.home.domain.model.OffersResponse
import com.test.home.domain.repository.HomeApi
import com.test.home.util.UiEventHome
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeModelView @Inject constructor(
    private val homeRepository: HomeApi
): ViewModel() {

    private val offers_ = MutableLiveData<OffersResponse>(null)
    val offers: LiveData<OffersResponse> = offers_

    private val townFrom_ = MutableLiveData<String>(null)
    val townFrom: LiveData<String> = townFrom_

    private val townTo_ = MutableLiveData<String>(null)
    val townTo: LiveData<String> = townTo_

    init {
        viewModelScope.launch {
            offers_.postValue(homeRepository.getOffers())
        }
    }

    fun onEvent(eventHome: UiEventHome){
        when(eventHome){
            is UiEventHome.SaveTownFrom -> townFrom_.postValue(eventHome.town)
            is UiEventHome.SaveTownTo -> townTo_.postValue(eventHome.town)
        }
    }

}