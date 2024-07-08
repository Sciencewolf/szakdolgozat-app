package com.sciencewolf.szakdolgozat.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.components.controlcomponents.ControlRaspberryPi
import com.sciencewolf.szakdolgozat.components.homecomponents.GetVersionComponent
import com.sciencewolf.szakdolgozat.components.homecomponents.GetDataFromDatabaseComponent
import io.github.jan.supabase.SupabaseClient

open class HomePage {
    private val showAppVersion = GetVersionComponent()
    private val showTempAndHum = ControlRaspberryPi()

    @Composable
    fun LoadHomePage(supabase: SupabaseClient) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(4.dp)
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Text(
                    text = "Welcome Back!",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(12.dp)
                )
                showAppVersion.GetAndDisplayVersion(supabase = supabase)
            }
            Spacer(modifier = Modifier)
            showTempAndHum.GetTemperatureAndHumiditySensor()
        }
    }
}