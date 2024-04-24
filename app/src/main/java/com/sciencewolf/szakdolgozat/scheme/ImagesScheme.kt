package com.sciencewolf.szakdolgozat.scheme

import kotlinx.serialization.Serializable

@Serializable
open class ImagesScheme (
    val id: Int,
    val url: String
)