package com.sciencewolf.szakdolgozat.scheme

import kotlinx.serialization.Serializable

@Serializable data class VersionScheme (
    val id: Int,
    val v: String
)