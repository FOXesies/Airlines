package com.test.core_navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test.core_navigation.util.UiMainEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainModelView @Inject constructor(): ViewModel() {

    private val uiEvent_ = MutableLiveData<UiMainEvent>()
    val uiEvent: LiveData<UiMainEvent> = uiEvent_

    fun setState(event: UiMainEvent){
        uiEvent_.postValue(event)
    }

}