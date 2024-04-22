package com.sciencewolf.szakdolgozat.schemes

import kotlinx.serialization.Serializable

@Serializable
data class GetDataScheme(
    val id: Int,
    val temp: String,
    val hum: String,
    val timewhen: String,
    val version: String
)