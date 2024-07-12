package com.test.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.home.data.repository.HomeApiImpl
import com.test.home.domain.repository.HomeApi
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeModelView @Inject constructor(
    private val homeRepository: HomeApi
): ViewModel() {

    init {
        viewModelScope.launch {
            val value = homeRepository.getOffers()
            value
        }
    }

}