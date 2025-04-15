package com.sciencewolf.szakdolgozat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.github.mikephil.charting.data.Entry
import com.sciencewolf.szakdolgozat.pages.home.LineChartView
import com.sciencewolf.szakdolgozat.pages.home.parseTimestampToFloat
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FullDataScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    val statsData = remember { mutableStateListOf<Pair<String, List<Entry>>>() }
    val humData = remember { mutableStateListOf<Pair<String, List<Entry>>>() }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val hatchingStartDate = remember { mutableStateOf<String?>(null) }

    val daysLoaded = remember { mutableStateOf(0) }
    val totalDays = remember { mutableStateOf(0) }
    val isLoading = remember { mutableStateOf(false) }

    // Initial fetch when screen opens
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val totalHatchingDays = fetchTotalHatchingDays()
            totalDays.value = totalHatchingDays - 1
            fetchNextDays(3, statsData, humData, errorMessage, hatchingStartDate, daysLoaded)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Full Hatching Data", color = Color.White)
        Spacer(modifier = Modifier.height(8.dp))

        if (statsData.isEmpty() && errorMessage.value == null) {
            Text("Loading data...", color = Color.Gray, modifier = Modifier.padding(16.dp))
        }

        errorMessage.value?.let {
            Text("Error: $it", color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        statsData.forEachIndexed { index, (date, tempEntries) ->
            val (humDate, humEntries) = humData.getOrNull(index) ?: (date to emptyList())

            if (tempEntries.isNotEmpty() || humEntries.isNotEmpty()) {
                ChartSection(title = "Temperature - $date", entries = tempEntries, label = "Temperature", color = Color.Red)
                ChartSection(title = "Humidity - $humDate", entries = humEntries, label = "Humidity", color = Color.Blue)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (daysLoaded.value < totalDays.value) {
            Button(
                onClick = {
                    isLoading.value = true
                    coroutineScope.launch {
                        fetchNextDays(3, statsData, humData, errorMessage, hatchingStartDate, daysLoaded)
                        isLoading.value = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading.value
            ) {
                Text(if (isLoading.value) "Loading..." else "Load More")
            }
        }

        Button(onClick = { navController.popBackStack() }, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}

@Composable
fun ChartSection(title: String, entries: List<Entry>, label: String, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, color = Color.White, modifier = Modifier.padding(bottom = 8.dp))
        LineChartView(entries = entries, label = label, color = color)
        Spacer(modifier = Modifier.height(24.dp))
    }
}

suspend fun fetchTotalHatchingDays(): Int {
    return try {
        val response = RetrofitInstance.api.getDay()
        response.body()?.response?.toIntOrNull() ?: 1
    } catch (e: Exception) {
        1
    }
}

suspend fun fetchNextDays(
    daysToLoad: Int,
    statsData: MutableList<Pair<String, List<Entry>>>,
    humData: MutableList<Pair<String, List<Entry>>>,
    errorMessage: MutableState<String?>,
    hatchingStartDate: MutableState<String?>,
    daysLoaded: MutableState<Int>
) {
    try {
        if (hatchingStartDate.value == null) {
            val totalDays = fetchTotalHatchingDays()
            hatchingStartDate.value = calculateStartDate(totalDays)
        }

        for (i in daysLoaded.value until (daysLoaded.value + daysToLoad)) {
            val day = calculateDateFromStart(hatchingStartDate.value!!, i)
            val response = RetrofitInstance.api.getStats(day)

            if (response.isSuccessful) {
                response.body()?.response?.let { dataList ->
                    val sampledEntries = dataList.filterIndexed { index, _ -> index % 50 == 0 }

                    val tempEntries = sampledEntries.mapNotNull { data ->
                        val timestamp = parseTimestampToFloat(data.timestamp ?: return@mapNotNull null)
                        val temp = data.data.temp?.toFloatOrNull() ?: return@mapNotNull null
                        Entry(timestamp, temp)
                    }

                    val humEntries = sampledEntries.mapNotNull { data ->
                        val timestamp = parseTimestampToFloat(data.timestamp ?: return@mapNotNull null)
                        val hum = data.data.hum?.toFloatOrNull() ?: return@mapNotNull null
                        Entry(timestamp, hum)
                    }

                    if (tempEntries.isNotEmpty()) statsData.add(day to tempEntries)
                    if (humEntries.isNotEmpty()) humData.add(day to humEntries)
                }
            }
        }

        daysLoaded.value += daysToLoad
    } catch (e: Exception) {
        errorMessage.value = e.message ?: "Error loading data."
    }
}

// 🔹 Calculate hatching start date
fun calculateStartDate(currentDay: Int?): String {
    if (currentDay == null || currentDay <= 0) return "Unknown Date"

    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -(currentDay - 1))
    return SimpleDateFormat("yyyy MMMM dd", Locale.getDefault()).format(calendar.time)
}

// 🔹 Convert day offset into YYYY-MM-DD format
fun calculateDateFromStart(startDate: String?, dayOffset: Int): String {
    return try {
        if (startDate.isNullOrBlank()) return "Unknown Date"

        val inputFormat = SimpleDateFormat("yyyy MMMM dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        calendar.time = inputFormat.parse(startDate) ?: return "Unknown Date"
        calendar.add(Calendar.DAY_OF_YEAR, dayOffset)

        outputFormat.format(calendar.time)
    } catch (e: Exception) {
        "Unknown Date"
    }
}
