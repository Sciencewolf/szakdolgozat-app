package com.sciencewolf.szakdolgozat.pages.home

import android.telecom.Call
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Severity
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.sciencewolf.szakdolgozat.pages.control.ControlRaspberryPi
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.ApiOkResponseList
import com.sciencewolf.szakdolgozat.utils.StatsResponse
import kotlinx.coroutines.launch

@Composable
fun LoadData(modifier: Modifier = Modifier, day: String) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        Log.d("LoadData", "Fetching data for day: $day")

        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getStats(day)
                Log.d("LoadData", "Data received: $response")
            } catch (e: Exception) {
                Log.e("LoadData", "API error occurred: ${e.message}")
            }
        }
    }

    Column {
        Text(text = "No data available")
    }
}
