package com.sciencewolf.szakdolgozat.utils

/**
 * SensorResponse data class
 */
data class SensorResponse(
    val temp: String = "",
    val hum: String = "",
    val timestamp: String = ""
)

/**
 * LidResponse data class
 */
data class LidResponse(
    val statusCode: Int = 0,
    val lid: String = "",
    val timestamp: String = ""
)