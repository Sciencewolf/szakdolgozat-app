package com.sciencewolf.szakdolgozat.pages.control

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.pages.home.formatDateString
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.*
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response
import java.time.LocalDate

open class ControlRaspberryPi {
    /**
     * Fetches API response on a background thread and handles errors.
     */
    suspend fun <T> fetchApiResponse(
        apiCall: suspend () -> Response<T>,
        onSuccess: (T) -> Unit,
        onError: (() -> Unit)? = null,
        context: Context
    ) {
        try {
            val response = apiCall()
            Log.d("API_CALL", "Response Code: ${response.code()}, Body: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {
                withContext(Dispatchers.Main) {
                    Log.d("API_SUCCESS", "Received data: ${response.body()}")
                    onSuccess(response.body()!!)
                }
            } else {
                withContext(Dispatchers.Main) {
                    Log.e("API_ERROR", "Error: ${response.errorBody()?.string()}")
                    onError?.invoke()
                    showToast(context, "Error fetching data")
                }
            }
        } catch (e: HttpException) {
            Log.e("API_ERROR", "HTTP Exception: ${e.message}")
            withContext(Dispatchers.Main) { showToast(context, "HTTP error") }
        } catch (e: Exception) {
            Log.e("API_ERROR", "General Exception: ${e.message}")
            withContext(Dispatchers.Main) { showToast(context, "Error occurred") }
        }
    }



    /**
     * Displays a Toast message. This function is **not** @Composable.
     */
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Fetches and displays temperature and humidity sensor data.
     */

    @Composable
    fun GetTemperatureAndHumiditySensor(
        modifier: Modifier = Modifier,
        enableSlider: Boolean = false
    ) {
        val context = LocalContext.current
        var sensorState by remember { mutableStateOf(SensorResponse()) }
        var sliderPosition by remember { mutableFloatStateOf(0f) }
        var isStopped by remember { mutableStateOf(false) }
        var manualTemperature by remember { mutableStateOf("") }
        var showButtons by remember { mutableStateOf(false) }

        val redRange = 39.0f..45.0f
        val scope = rememberCoroutineScope()

        LaunchedEffect(isStopped) {
            while (!isStopped) {
                fetchApiResponse(
                    apiCall = { RetrofitInstance.api.getTemperatureAndHumidity() },
                    onSuccess = {
                        sensorState = it
                        sliderPosition = it.temp.split(" ")[0].toFloat()
                    },
                    onError = {
                        showToast(context, "Sensor is offline")
                    },
                    context = context
                )
                delay(10_000)
            }
        }

        Column(modifier = modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_temperature_100),
                    contentDescription = stringResource(R.string.temp_icon_label)
                )

                Text(text = if (isStopped) manualTemperature else sensorState.temp.ifEmpty {
                    stringResource(
                        R.string.undefined
                    )
                })

                Spacer(modifier = Modifier.width(8.dp))

                if (enableSlider) {
                    Slider(
                        value = sliderPosition,
                        onValueChange = {
                            sliderPosition = it
                            isStopped = true
                            manualTemperature = String.format("%.1f°C", it)
                            showButtons = true
                        },
                        valueRange = 15.0f..45.0f,
                        steps = ((45 - 15) * 10 - 1).toInt(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.White,
                            activeTrackColor = if (sliderPosition in redRange) Color.Red else Color.Green
                        )
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.icons8_humidity_100),
                    contentDescription = stringResource(R.string.hum_icon_label)
                )
                Text(text = sensorState.hum.ifEmpty { stringResource(R.string.undefined) })
            }

            if (enableSlider && showButtons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            isStopped = false
                            showButtons = false
                            scope.launch {
                                try {
                                    val response =
                                        RetrofitInstance.api.setTemperature(sliderPosition)
                                    if (response.isSuccessful) {
                                        showToast(context, "Temperature set to ${sliderPosition}°C")
                                        showButtons = false
                                    } else {
                                        showToast(context, "Failed to set temperature")
                                    }
                                } catch (e: Exception) {
                                    showToast(context, "Error setting temperature: ${e.message}")
                                }
                            }
                        }
                    ) {
                        Text("Ok")
                    }

                    TextButton(
                        onClick = {
                            isStopped = false
                            showButtons = false
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
                delay(10_000)
            }
        }

        Row(modifier = Modifier.padding(horizontal = 18.dp)) {
            Text(
                text = if (lidState.value.lid
                        .lowercase()
                        .ifEmpty { stringResource(R.string.undefined) } == "close"
                ) "Lid is closed"
                else "Lid is open")
        }
    }

    suspend fun turnOnCooler() {
        try {
            val response = RetrofitInstance.api.onCooler()
            if (!response.isSuccessful) throw Exception("Failed to turn on Cooler")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    suspend fun turnOffCooler() {
        try {
            val response = RetrofitInstance.api.offCooler()
            if (!response.isSuccessful) throw Exception("Failed to turn off Cooler")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    suspend fun turnOnHeatingElement() {
        try {
            val response = RetrofitInstance.api.onHeatingElement()
            if (!response.isSuccessful) throw Exception("Failed to turn on Heating")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    suspend fun turnOffHeatingElement() {
        try {
            val response = RetrofitInstance.api.offHeatingElement()
            if (!response.isSuccessful) throw Exception("Failed to turn off Heating")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }

    /**
     * Get last eggs rotation.
     */
    @Composable
    fun GetLastEggsRotation(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val lastTime = remember { mutableStateOf(ApiOkResponse()) }

        val formattedDay = formatDateString(lastTime.value.response, "yyyy MMMM dd HH:mm")

        LaunchedEffect(Unit) {
            while (true) {
                fetchApiResponse(
                    apiCall = { RetrofitInstance.api.getLastEggsRotation() },
                    onSuccess = { lastTime.value = it },
                    onError = {
                        if (!OtherOfflineToastManager.toast) {
                            showToast(context, "Unknown error")
                            OtherOfflineToastManager.toast = true
                        }
                    },
                    context = context
                )
                delay(100_000)
            }
        }

        Row(modifier = Modifier.padding(18.dp)) {
            Text(text = "Last egg rotation: " + formattedDay.ifEmpty { stringResource(R.string.undefined) })
        }
    }

    /**
     * Get hatching day.
     */
    @Composable
    fun GetDay(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val day = remember { mutableStateOf(ApiOkResponse()) }

        LaunchedEffect(Unit) {
            fetchApiResponse(
                apiCall = { RetrofitInstance.api.getDay() },
                onSuccess = { day.value = it },
                onError = {
                    if (!OtherOfflineToastManager.toast) {
                        showToast(context, "Unknown error")
                        OtherOfflineToastManager.toast = true
                    }
                },
                context = context
            )
        }

        Column(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            val dayText = day.value.response.ifEmpty { "1" }
            val progressValue = dayText.toFloatOrNull() ?: 1f

            Text(
                text = "Hatching at Day $dayText of 21",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearProgressIndicator(
                progress = progressValue / 21f,
                trackColor = Color.White,
                strokeCap = StrokeCap.Round,
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Days remaining: ${21 - progressValue.toInt()} " +
                        "(${
                            LocalDate.now()
                                .plusDays((21 - progressValue.toInt()).toLong())
                        })",
                fontSize = 20.sp
            )
        }
    }

    @Composable
    fun RotateEggs(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        var isRotating by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope()

        Card {
            Row(
                modifier = Modifier
                    .size(width = 250.dp, height = 50.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.icons8_rotate_100),
                        contentDescription = "fan icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rotate eggs")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    modifier = Modifier.background(Color.Transparent),
                    checked = isRotating,
                    onCheckedChange = { checked ->
                        isRotating = checked
                        if (checked) {
                            coroutineScope.launch {
                                try {
                                    val response = RetrofitInstance.api.rotateEggs()
                                    if (response.isSuccessful) {
                                        showToast(context, "Eggs rotated successfully")
                                    } else {
                                        showToast(context, "Failed to rotate eggs")
                                        isRotating = false
                                    }
                                } catch (e: Exception) {
                                    showToast(context, "Error rotating eggs: ${e.message}")
                                    isRotating = false
                                }
                            }
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = if (isRotating) Color.Green else Color.Gray,
                    )
                )
            }
        }
    }

    @Composable
    fun LedIndication(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        var ledState by remember { mutableStateOf(false) }

        Card {
            Row(
                modifier = Modifier
                    .size(width = 250.dp, height = 50.dp)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painterResource(R.drawable.icons8_led_100),
                        contentDescription = "led icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "LED Indication")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Switch(
                    modifier = Modifier.background(Color.Transparent),
                    checked = ledState,
                    onCheckedChange = { checked ->
                        ledState = checked
                        coroutineScope.launch {
                            try {
                                val response =
                                    RetrofitInstance.api.ledIndication(if (ledState) "on" else "off")
                                if (response.isSuccessful) {
                                    showToast(
                                        context,
                                        "led indication is set to ${if (ledState) "on" else "off"}"
                                    )
                                } else {
                                    showToast(context, "Failed to set led indication")
                                    ledState = false
                                }
                            } catch (e: Exception) {
                                showToast(context, "Error led indication: ${e.message}")
                                ledState = false
                            }
                        }
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = if (ledState) Color.Green else Color.Gray,
                    )
                )
            }
        }
    }

    @Composable
    fun HatchingActions(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        var isHatching by remember { mutableStateOf(false) }
        var showDialog by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            fetchApiResponse(
                apiCall = { RetrofitInstance.api.isHatching() },
                onSuccess = { isHatching = it.response.toBoolean() },
                onError = {
                    if (!OtherOfflineToastManager.toast) {
                        showToast(context, "Unknown error")
                        OtherOfflineToastManager.toast = true
                    }
                },
                context = context
            )
        }

        Column(
            modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Hatching Action", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))

            if (isHatching) {
                TextButton(
                    onClick = {
                        showDialog = true
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Red
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icons8_stop_100),
                        contentDescription = "stop icon"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Stop Hatching")
                }
            } else {
                Row(
                    modifier = Modifier.padding(vertical = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                fetchApiResponse(
                                    apiCall = { RetrofitInstance.api.resumeHatching() },
                                    onSuccess = { isHatching = true },
                                    onError = {
                                        if (!OtherOfflineToastManager.toast) {
                                            showToast(context, "Unknown error")
                                            OtherOfflineToastManager.toast = true
                                        }
                                    },
                                    context = context
                                )
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icons8_continue_100),
                            contentDescription = "continue icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Continue Hatching")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(
                        onClick = {
                            coroutineScope.launch {
                                fetchApiResponse(
                                    apiCall = { RetrofitInstance.api.startHatching() },
                                    onSuccess = { isHatching = true },
                                    onError = {
                                        if (!OtherOfflineToastManager.toast) {
                                            showToast(context, "Unknown error")
                                            OtherOfflineToastManager.toast = true
                                        }
                                    },
                                    context = context
                                )
                            }
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = Color.Green
                        )
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icons8_start_100),
                            contentDescription = "start icon"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Start Hatching",
                        )
                    }
                }
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = { Text(text = "Stop Hatching") },
                    text = { Text(text = stringResource(R.string.confirm_hatching_stop_text)) },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                coroutineScope.launch {
                                    fetchApiResponse(
                                        apiCall = { RetrofitInstance.api.stopHatching() },
                                        onSuccess = { isHatching = false },
                                        onError = {
                                            if (!OtherOfflineToastManager.toast) {
                                                showToast(context, "Unknown error")
                                                OtherOfflineToastManager.toast = true
                                            }
                                        },
                                        context = context
                                    )
                                }
                            }
                        ) {
                            Text(text = "Ok")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    }

                )
            }
        }
    }
}