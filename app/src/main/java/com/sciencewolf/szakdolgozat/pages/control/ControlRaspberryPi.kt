package com.sciencewolf.szakdolgozat.pages.control

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.*
import kotlinx.coroutines.*
import retrofit2.HttpException

open class ControlRaspberryPi {
    /**
     * Fetches API response on a background thread and handles errors.
     */
    private suspend fun <T> fetchApiResponse(
        apiCall: suspend () -> retrofit2.Response<T>,
        onSuccess: (T) -> Unit,
        onError: (() -> Unit)? = null,
        toastManager: (() -> Boolean)? = null,
        errorMessage: String = "error",
        context: Context
    ) {
        try {
            val response = apiCall()
            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) { onSuccess(response.body()!!) }
            } else {
                withContext(Dispatchers.Main) {
                    onError?.invoke()
                    toastManager?.let {
                        if (!it()) {
                            showToast(context, errorMessage)
                        }
                    }
                }
            }
        } catch (e: HttpException) {
            withContext(Dispatchers.Main) { showToast(context, "HTTP error") }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) { showToast(context, errorMessage) }
        }
    }

    /**
     * Displays a Toast message. This function is **not** @Composable.
     */
    private fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Fetches and displays temperature and humidity sensor data.
     */

    @Composable
    fun GetTemperatureAndHumiditySensor(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var sensorState by remember { mutableStateOf(SensorResponse()) }
        var sliderPosition by remember { mutableFloatStateOf(0f) }
        var isStopped by remember { mutableStateOf(false) } // Flag to stop API calls
        var lastHumidity by remember { mutableStateOf("") } // Store last fetched humidity
        var manualTemperature by remember { mutableStateOf("") } // Store manually adjusted temperature
        var showButtons by remember { mutableStateOf(false) } // Show buttons when slider moves

        val redRange = 39.0f..45.0f
        val scope = rememberCoroutineScope() // ✅ Use this for launching coroutines in Compose

        // Fetch API data until stopped
        LaunchedEffect(isStopped) {
            while (!isStopped) {
                fetchApiResponse(
                    apiCall = { RetrofitInstance.api.getTemperatureAndHumidity() },
                    onSuccess = {
                        sensorState = it
                        sliderPosition = it.temp.split(" ")[0].toFloat()
                        lastHumidity = it.hum // Store API humidity before stopping
                    },
                    onError = {
                        if (!SensorOfflineToastManager.toast) {
                            showToast(context, "Sensor is offline")
                            SensorOfflineToastManager.toast = true
                        }
                    },
                    context = context
                )
                delay(5000) // 5 sec refresh
            }
        }

        Column(modifier = Modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_temperature_100),
                    contentDescription = stringResource(R.string.temp_icon_label)
                )

                // Display manually adjusted temperature if API is stopped, else API temperature
                Text(text = if (isStopped) manualTemperature else sensorState.temp.ifEmpty {
                    stringResource(
                        R.string.undefined
                    )
                })

                Slider(
                    value = sliderPosition,
                    onValueChange = {
                        sliderPosition = it  // Update the displayed value
                        isStopped = true     // Stop the API call
                        manualTemperature = String.format(
                            "%.1f°C",
                            it
                        ) // Store manually adjusted temperature with one decimal place
                        showButtons = true // Show buttons when slider moves
                    },
                    valueRange = 15.0f..45.0f,
                    steps = ((45 - 15) * 10 - 1).toInt(), // Step size = 0.1
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = if (sliderPosition in redRange) Color.Red else Color.Green
                    )
                )

                // Show the humidity from API before stopping OR the latest API humidity
                Text(text = sensorState.hum.ifEmpty { stringResource(R.string.undefined) })
            }

            if (showButtons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    TextButton(
                        onClick = {
                            isStopped = false // Restart fetching data
                            showButtons = false // Hide buttons
                            scope.launch {
                                try {
                                    val response = RetrofitInstance.api.setTemperature(sliderPosition)
                                    println(response)
                                    if (response.isSuccessful && response.body() != null) {
                                        showToast(context, "Temperature set to ${sliderPosition}°C")
                                        showButtons = false
                                    } else {
                                        val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                                        showToast(context, "Failed to set temperature: $errorMsg")
                                    }
                                } catch (e: Exception) {
                                    showToast(context, "Error setting temperature: ${e.message}")
                                }
                            }
                        }

                    ) {
                        Text("Set Temperature")
                    }

                    TextButton(
                        onClick = {
                            isStopped = false // Restart fetching data
                            showButtons = false // Hide buttons
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }

    /**
     * Fetches and displays lid status.
     */
    @Composable
    fun GetLidStatus(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val lidState = remember { mutableStateOf(LidResponse()) }

        LaunchedEffect(Unit) {
            while (true) {
                fetchApiResponse(
                    apiCall = { RetrofitInstance.api.getLidStatus() },
                    onSuccess = { lidState.value = it },
                    onError = {
                        if (!LidSensorOfflineToastManager.toast) {
                            showToast(context, "Lid sensor is offline")
                            LidSensorOfflineToastManager.toast = true
                        }
                    },
                    context = context
                )
                delay(2000)
            }
        }

        Row(modifier = Modifier) {
            Text(text = "Lid is: " + lidState.value.lid.ifEmpty { stringResource(R.string.undefined) })
        }
    }

    /**
     * Controls the cooler state.
     */
    @Composable
    fun Cooler(modifier: Modifier = Modifier, on: Boolean) {
        ControlDevice(
            modifier = modifier,
            on = on,
            onCall = { RetrofitInstance.api.onCooler() },
            offCall = { RetrofitInstance.api.offCooler() },
            toastManager = { CoolerOfflineToastManager.toast },
            errorMessage = "Cooler is not working"
        )
    }

    /**
     * Controls the heating element state.
     */
    @Composable
    fun HeatingElement(modifier: Modifier = Modifier, on: Boolean) {
        ControlDevice(
            modifier = modifier,
            on = on,
            onCall = { RetrofitInstance.api.onHeatingElement() },
            offCall = { RetrofitInstance.api.offHeatingElement() },
            toastManager = { HeatingElementOfflineToastManager.toast },
            errorMessage = "Heating element is not working"
        )
    }


    @Composable
    fun RotateEggs(modifier: Modifier = Modifier, on: Boolean) {
        ControlDevice(
            modifier = modifier,
            on = on,
            onCall = { RetrofitInstance.api.rotateEggs() },
            offCall = { RetrofitInstance.api.getLastEggsRotation() },
            toastManager = { RotateEggsOfflineToastManager.toast },
            errorMessage = "Rotating eggs is not working"
            )
    }

    /**
     * Generic function for controlling devices (Cooler, Heating Element).
     */
    @Composable
    fun ControlDevice(
        modifier: Modifier = Modifier,
        on: Boolean,
        onCall: suspend () -> retrofit2.Response<ApiOkResponse>,
        offCall: suspend () -> retrofit2.Response<ApiOkResponse>,
        toastManager: () -> Boolean,
        errorMessage: String
    ) {
        val context = LocalContext.current
        val deviceState = remember { mutableStateOf(ApiOkResponse()) }
        var hasLoaded by remember { mutableStateOf(false) } // ✅ Prevent API call on first load

        LaunchedEffect(key1 = on) {
            if (hasLoaded) { // ✅ Prevent the first automatic API call
                CoroutineScope(Dispatchers.IO).launch {
                    fetchApiResponse(
                        apiCall = { if (on) onCall() else offCall() },
                        onSuccess = { deviceState.value = it },
                        toastManager = toastManager,
                        errorMessage = errorMessage,
                        context = context
                    )
                }
            } else {
                hasLoaded = true // ✅ Set to true after first load, preventing unwanted calls
            }
        }
    }
}