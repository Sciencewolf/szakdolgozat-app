package com.sciencewolf.szakdolgozat.utils

import kotlinx.serialization.Serializable

@Serializable
data class DataScheme(
    val id: Int,
    val temp: String,
    val hum: String,
    val timewhen: String
)