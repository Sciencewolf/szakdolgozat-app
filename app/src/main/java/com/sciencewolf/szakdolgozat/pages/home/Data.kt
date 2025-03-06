package com.sciencewolf.szakdolgozat.pages.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.mikephil.charting.data.Entry
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.StatsResponse
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LoadData(modifier: Modifier = Modifier, day: String) {
    val coroutineScope = rememberCoroutineScope()

    val statsResponse = remember { mutableStateOf<StatsResponse?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val tempEntries = remember { mutableStateOf<List<Entry>>(emptyList()) }
    val humEntries = remember { mutableStateOf<List<Entry>>(emptyList()) }

    val formattedDay = formatDateString(day, "yyyy MMMM dd")

    LaunchedEffect(Unit) {
        Log.d("LoadData", "Fetching data for day: $day")

        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getStats(day)

                if (response.isSuccessful) {
                    statsResponse.value = response.body()
                    Log.d("LoadData", "Data received: ${statsResponse.value}")

                    // Convert API response to chart entries
                    statsResponse.value?.response?.let { dataList ->
                        val sampledEntries = dataList
                            .filterIndexed { index, _ -> index % 20 == 0 } // Take every 50th entry

                        tempEntries.value = sampledEntries.map { data ->
                            val timestamp = parseTimestampToFloat(data.timestamp)
                            Entry(timestamp, data.data.temp.toFloat()) // Temperature
                        }

                        humEntries.value = sampledEntries.map { data ->
                            val timestamp = parseTimestampToFloat(data.timestamp)
                            Entry(timestamp, data.data.hum.toFloat()) // Humidity
                        }
                    }

                } else {
                    errorMessage.value = response.errorBody()?.string()
                    Log.e("LoadData", "API Error: ${errorMessage.value}")
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
                Log.e("LoadData", "API error occurred: ${errorMessage.value}")
            }
        }
    }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Temperature Data - $formattedDay(today)", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        if (tempEntries.value.isNotEmpty()) {
            LineChartView(entries = tempEntries.value, label = "Temperature", color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Humidity Data - $formattedDay(today)", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        if (humEntries.value.isNotEmpty()) {
            LineChartView(entries = humEntries.value, label = "Humidity", color = Color.Blue)
        } else if (errorMessage.value != null) {
            Text(text = "Error: ${errorMessage.value}", color = Color.Red)
        } else {
            Text(text = stringResource(R.string.loading_data_text))
        }
    }
}

fun parseTimestampToFloat(timestamp: String): Float {
    return try {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = sdf.parse(timestamp)
        date?.time?.div(1000)?.toFloat() ?: 0f
    } catch (e: Exception) {
        0f
    }
}