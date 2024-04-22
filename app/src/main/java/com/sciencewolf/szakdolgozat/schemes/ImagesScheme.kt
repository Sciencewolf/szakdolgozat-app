package com.sciencewolf.szakdolgozat.schemes

import kotlinx.serialization.Serializable

@Serializable
data class ImagesScheme (
    val id: Int,
    val url: String
)