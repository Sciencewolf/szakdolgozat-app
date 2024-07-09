package com.sciencewolf.szakdolgozat.rpiapi

import com.sciencewolf.szakdolgozat.utils.LEDResponse
import com.sciencewolf.szakdolgozat.utils.SensorResponse
import retrofit2.Response
import retrofit2.http.GET;

interface ApiService {
    @GET("/get-temp-hum")
    suspend fun getTemperatureAndHumidity(): Response<SensorResponse>

    @GET("/on-red-led")
    suspend fun onRedLed(): Response<LEDResponse>

    @GET("/off-red-led")
    suspend fun offRedLed(): Response<LEDResponse>

    @GET("/on-green-led")
    suspend fun onGreenLed(): Response<LEDResponse>

    @GET("/off-green-led")
    suspend fun offGreenLen(): Response<LEDResponse>

    @GET("/on-blue-led")
    suspend fun onBlueLed(): Response<LEDResponse>

    @GET("/off-blue-led")
    suspend fun offBlueLed(): Response<LEDResponse>

    @GET("/on-all-led")
    suspend fun onAllLed(): Response<LEDResponse>

    @GET("/off-all-led")
    suspend fun offAllLed(): Response<LEDResponse>
}
