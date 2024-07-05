package com.sciencewolf.szakdolgozat.scheme

import kotlinx.serialization.Serializable

data class SzakdolgozatApiResponse (
    val temp: String = "",
    val hum: String = "",
    val timestamp: String = ""
)