package com.test.home.util

sealed class UiEventHome {
    data class SaveTownTo(val town: String): UiEventHome()
    data class SaveTownFrom(val town: String): UiEventHome()
}