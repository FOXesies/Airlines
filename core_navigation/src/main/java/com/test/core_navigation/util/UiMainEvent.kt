package com.test.core_navigation.util

sealed class UiMainEvent {
    data object OpenHome: UiMainEvent()
    data object OpenTicketPreview: UiMainEvent()
    data object OpenTicketList: UiMainEvent()
}