package com.sciencewolf.szakdolgozat.utils

data class ApiOkResponse(
    val statusCode: Int = 0,
    val response: String = "",
    val timestamp: String = "",
    val other: List<String> = listOf()
)

data class ApiNotFoundResponse(
    val statusCode: Int = 0,
    val response: String = "",
    val other: List<String> = listOf()
)

data class ApiNotImplementedResponse(
    val response: String = "",
    val statusCode: Int = 0,
    val other: List<String> = listOf()
)