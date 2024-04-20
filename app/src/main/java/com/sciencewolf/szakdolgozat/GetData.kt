package com.sciencewolf.szakdolgozat

import kotlinx.serialization.Serializable

@Serializable
data class GetData(
    val id: Int,
    val temp: String,
    val hum: String,
    val timewhen: String
)