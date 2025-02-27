package com.sciencewolf.szakdolgozat.utils

/**
 * SensorResponse data class
 */
data class SensorResponse(
    val temp: String = "",
    val hum: String = "",
    val timestamp: String = ""
)

data class StatsResponse(
    val other: Any?,
    val response: List<StatsData>,
    val status_code: Int,
    val timestamp: String
)

data class StatsData(
    val data: SensorData,
    val timestamp: String
)

data class SensorData(
    val temp: String,
    val hum: String
)


/**
 * LidResponse data class
 */
data class LidResponse(
    val statusCode: Int = 0,
    val lid: String = "",
    val timestamp: String = ""
)