package com.sciencewolf.szakdolgozat.rpiapi

import com.sciencewolf.szakdolgozat.scheme.SzakdolgozatApiResponseSensor
import retrofit2.Response
import retrofit2.http.GET;

interface ApiService {
    @GET("/get-temp-hum")
    suspend fun getTemperatureAndHumidity(): Response<SzakdolgozatApiResponseSensor>

    @GET("/on-red-led")
    suspend fun onRedLed(): Response<LED>

    @GET("/off-red-led")
    suspend fun offRedLed(): Response<LED>

    @GET("/on-green-led")
    suspend fun onGreenLed(): Response<LED>

    @GET("/off-green-led")
    suspend fun offGreenLen(): Response<LED>

    @GET("/on-blue-led")
    suspend fun onBlueLed(): Response<LED>

    @GET("/off-blue-led")
    suspend fun offBlueLed(): Response<LED>

    @GET("/on-all-led")
    suspend fun onAllLed(): Response<LED>

    @GET("/off-all-led")
    suspend fun offAllLed(): Response<LED>
}
