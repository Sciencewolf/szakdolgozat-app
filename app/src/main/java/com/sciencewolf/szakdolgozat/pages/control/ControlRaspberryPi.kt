package com.sciencewolf.szakdolgozat.pages.control

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.utils.LEDResponse
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.SensorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds


object LedOfflineToastManager {
    var toast by mutableStateOf(false)
}

object SensorOfflineToastManager {
    var toast by mutableStateOf(false)
}

open class ControlRaspberryPi {

    @Composable
    fun ControlLed(
        ledColor: String,
        on: Boolean,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            val context = LocalContext.current
            var ledState by remember { mutableStateOf(LEDResponse()) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = on) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        when (ledColor) {
                            "Red" -> if (on) RetrofitInstance.api.onRedLed()
                            else RetrofitInstance.api.offRedLed()
                            "Green" -> if (on) RetrofitInstance.api.onGreenLed()
                            else RetrofitInstance.api.offGreenLen()
                            "Blue" -> if (on) RetrofitInstance.api.onBlueLed()
                            else RetrofitInstance.api.offBlueLed()
                            "All" -> if (on) RetrofitInstance.api.onAllLed()
                            else RetrofitInstance.api.offAllLed()

                            else -> throw IllegalArgumentException("Unknown LED color")
                        }
                    } catch (ex: HttpException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "HTTP error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@launch
                    } catch (ex: java.io.IOException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "IO error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@launch
                    }

                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            ledState = response.body()!!
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            if (!LedOfflineToastManager.toast) {
                                Toast.makeText(
                                    context,
                                    "LED's is offline",
                                    Toast.LENGTH_SHORT
                                ).show()
                                LedOfflineToastManager.toast = true
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun GetTemperatureAndHumiditySensor(modifier: Modifier = Modifier) {
        Column (
            modifier = Modifier.fillMaxHeight()
        ) {
            val scope = rememberCoroutineScope()
            val context = LocalContext.current
            var sensorState by remember { mutableStateOf(SensorResponse()) }

            LaunchedEffect(key1 = true) {
                scope.launch {
                    val response = try {
                        RetrofitInstance.api.getTemperatureAndHumidity()
                    } catch (ex: HttpException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "http error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@launch
                    } catch (ex: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                context,
                                "error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        return@launch
                    }

                    if (response.body() != null && response.isSuccessful) {
                        withContext(Dispatchers.Main) {
                            sensorState = response.body()!!
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            if (!SensorOfflineToastManager.toast) {
                                Toast.makeText(
                                    context,
                                    "sensor is offline",
                                    Toast.LENGTH_SHORT
                                ).show()
                                SensorOfflineToastManager.toast = true
                            }
                        }
                    }
                }
            }

            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_temperature_100),
                    contentDescription = stringResource(R.string.temp_icon_label)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = sensorState.temp.ifEmpty { stringResource(R.string.undefined) })
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_humidity_100),
                    contentDescription = stringResource(R.string.hum_icon_label)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = sensorState.hum.ifEmpty { stringResource(R.string.undefined) })
            }
        }
    }
}