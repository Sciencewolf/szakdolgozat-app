package com.sciencewolf.szakdolgozat.rpiapi

import com.sciencewolf.szakdolgozat.utils.LidResponse
import com.sciencewolf.szakdolgozat.utils.ApiOkResponse
import com.sciencewolf.szakdolgozat.utils.SensorResponse
import retrofit2.Response
import retrofit2.http.GET;
import retrofit2.http.Query

interface ApiService {
    @GET("/get-temp-hum")
    suspend fun getTemperatureAndHumidity(): Response<SensorResponse>

    @GET("/get-lid-status")
    suspend fun getLidStatus(): Response<LidResponse>

    @GET("/prepare-hatching")
    suspend fun prepareHatching(): Response<ApiOkResponse>

    @GET("/start-hatching")
    suspend fun startHatching(): Response<ApiOkResponse>

    @GET("/stop-hatching")
    suspend fun stopHatching(): Response<ApiOkResponse>

    @GET("/is-hatching")
    suspend fun isHatching(): Response<ApiOkResponse>

    @GET("/set-temp")
    suspend fun setTemperature(@Query("t") temperature: Float): Response<ApiOkResponse>

    @GET("/led-indication")
    suspend fun ledIndication(@Query("val") value: String): Response<ApiOkResponse>

    @GET("/get-day")
    suspend fun getDay(): Response<ApiOkResponse>

    @GET("/get-stats")
    suspend fun getStats(@Query("day") day: String): Response<ApiOkResponse>

    @GET("/on-cooler")
    suspend fun onCooler(): Response<ApiOkResponse>

    @GET("/off-cooler")
    suspend fun offCooler(): Response<ApiOkResponse>

    @GET("/on-heating-element")
    suspend fun onHeatingElement(): Response<ApiOkResponse>

    @GET("/off-heating-element")
    suspend fun offHeatingElement(): Response<ApiOkResponse>

    @GET("/rotate-eggs")
    suspend fun rotateEggs(): Response<ApiOkResponse>

    @GET("/get-last-eggs-rotation")
    suspend fun getLastEggsRotation(): Response<ApiOkResponse>

    @GET("/overall")
    suspend fun getOverall(): Response<ApiOkResponse>

    @GET("/health")
    suspend fun getHealth(): Response<ApiOkResponse>

    @GET("/alive")
    suspend fun getAlive(): Response<ApiOkResponse>

    @GET("/shutdown")
    suspend fun shutdown(): Response<ApiOkResponse>
}
