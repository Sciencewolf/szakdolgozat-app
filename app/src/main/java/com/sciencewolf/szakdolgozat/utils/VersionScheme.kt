package com.sciencewolf.szakdolgozat.utils

import kotlinx.serialization.Serializable

@Serializable data class VersionScheme (
    val id: Int,
    val v: String
)