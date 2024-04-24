package com.sciencewolf.szakdolgozat.scheme

import kotlinx.serialization.Serializable

@Serializable
data class DataScheme(
    val id: Int,
    val temp: String,
    val hum: String,
    val timewhen: String
)