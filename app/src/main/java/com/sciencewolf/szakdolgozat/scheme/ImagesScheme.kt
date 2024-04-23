package com.sciencewolf.szakdolgozat.scheme

import kotlinx.serialization.Serializable

@Serializable
data class ImagesScheme (
    val id: Int,
    val url: String,
    val base64text: String
)