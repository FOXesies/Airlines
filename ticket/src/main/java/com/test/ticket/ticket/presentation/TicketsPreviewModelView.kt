package com.test.ticket.ticket.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.ticket.ticket.domain.model.ResponseTicketPreview
import com.test.ticket.ticket.domain.repository.TicketsPreviewApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsPreviewModelView @Inject constructor(
    private val ticketsPreviewApi: TicketsPreviewApi
): ViewModel() {

    private val tickets_ = MutableLiveData<ResponseTicketPreview>(null)
    val tickets: LiveData<ResponseTicketPreview> = tickets_

    init {
        loadTickets()
    }

    private fun loadTickets(){
        viewModelScope.launch {
            tickets_.postValue(ticketsPreviewApi.getTickets())
        }
    }

}