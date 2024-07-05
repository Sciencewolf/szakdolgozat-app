package com.sciencewolf.szakdolgozat.components.controlcomponents

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.sciencewolf.szakdolgozat.rpiapi.LED
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ControlRaspberryPi {

    @Composable
    fun controlLed(
        ledColor: String,
        on: Boolean,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            val context = LocalContext.current
            var ledState by remember { mutableStateOf(LED()) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = on) {
                scope.launch(Dispatchers.IO) {
                    val response = try {
                        when (ledColor) {
                            "Red" -> if (on) RetrofitInstance.api.onRedLed() else RetrofitInstance.api.offRedLed()
                            "Green" -> if (on) RetrofitInstance.api.onGreenLed() else RetrofitInstance.api.offGreenLen()
                            "Blue" -> if (on) RetrofitInstance.api.onBlueLed() else RetrofitInstance.api.offBlueLed()
                            "All" -> if (on) RetrofitInstance.api.onAllLed() else RetrofitInstance.api.offAllLed()
                            else -> throw IllegalArgumentException("Unknown LED color")
                        }
                    } catch (ex: HttpException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "HTTP error", Toast.LENGTH_LONG).show()
                        }
                        return@launch
                    } catch (ex: java.io.IOException) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "IO error", Toast.LENGTH_SHORT).show()
                        }
                        return@launch
                    }

                    if (response.isSuccessful && response.body() != null) {
                        withContext(Dispatchers.Main) {
                            ledState = response.body()!!
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun getTemperatureAndHumiditySensor(modifier: Modifier = Modifier) {
        TODO()
    }
}
