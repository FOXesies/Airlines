package com.test.tickets.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.tickets.domain.model.ResponseTicket
import com.test.tickets.domain.repository.TicketsListApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TicketsModelView @Inject constructor(
    private val ticketsListApi: TicketsListApi
): ViewModel() {

    private val tickets_ = MutableLiveData<ResponseTicket?>(null)
    val tickets: LiveData<ResponseTicket?> = tickets_

    init {
        loadTickets()
    }

    private fun loadTickets() {
        viewModelScope.launch {
            tickets_.postValue(ticketsListApi.getTickets())
        }
    }

}