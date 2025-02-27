package com.sciencewolf.szakdolgozat.pages.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.rpiapi.RetrofitInstance
import com.sciencewolf.szakdolgozat.utils.OtherOfflineToastManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class ControlPage {
    private val controlRaspberryPi = ControlRaspberryPi()

    @Composable
    fun LoadControlPage(
        modifier: Modifier = Modifier
    ) {
        var switchStateCooler by remember { mutableStateOf(false) }
        var switchStateHeatingElement by remember { mutableStateOf(false) }

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            while (true) {
                controlRaspberryPi.fetchApiResponse(
                    apiCall = { RetrofitInstance.api.getOverall() },
                    onSuccess = { response ->
                        val activeProcesses = response.response

                        switchStateCooler = activeProcesses.contains("cooler")
                        switchStateHeatingElement = activeProcesses.contains("heating_element")
                    },
                    onError = {
                        if (!OtherOfflineToastManager.toast) {
                            controlRaspberryPi.showToast(context, "Processes data unavailable")
                            OtherOfflineToastManager.toast = true
                        }
                    },
                    context = context
                )
                delay(30_000)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ControlRow(
                label = stringResource(R.string.cooler_text),
                switchState = switchStateCooler,
                onSwitchChange = { newState ->
                    switchStateCooler = newState
                    coroutineScope.launch {
                        if (newState) {
                            controlRaspberryPi.turnOnCooler()
                        } else {
                            controlRaspberryPi.turnOffCooler()
                        }
                    }
                },
                icon = painterResource(R.drawable.icons8_fan_100),
                width = 250.dp
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ControlRow(
                label = stringResource(R.string.heating_element_text),
                switchState = switchStateHeatingElement,
                onSwitchChange = { newState ->
                    switchStateHeatingElement = newState
                    coroutineScope.launch {
                        if (newState) {
                            controlRaspberryPi.turnOnHeatingElement()
                        } else {
                            controlRaspberryPi.turnOffHeatingElement()
                        }
                    }
                },
                width = 250.dp,
                icon = painterResource(R.drawable.icons8_heating_100)
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            controlRaspberryPi.RotateEggs(modifier = Modifier)

            Spacer(modifier = Modifier.padding(vertical = 8.dp))

            controlRaspberryPi.LedIndication(modifier = Modifier)
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Divider(modifier = Modifier)

            controlRaspberryPi.HatchingActions(modifier = Modifier)
            Divider(modifier = Modifier)

            Column(
                modifier = Modifier.padding(vertical = 20.dp)
            ) {
                Text(
                    text = "Set Temperature",
                    fontSize = 20.sp
                )
                controlRaspberryPi.GetTemperatureAndHumiditySensor(enableSlider = true)
            }
        }
    }

    @Composable
    private fun ControlRow(
        label: String,
        switchState: Boolean,
        onSwitchChange: (Boolean) -> Unit,
        width: Dp = 150.dp,
        height: Dp = 50.dp,
        icon: Painter
    ) {
        Card {
            Row(
                modifier = Modifier
                    .size(width = width, height = height)
                    .padding(vertical = 8.dp, horizontal = 8.dp)
                    .background(Color.Transparent),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(painter = icon, contentDescription = "icon")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = label)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Switch(
                    checked = switchState,
                    onCheckedChange = { onSwitchChange(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = if (switchState) Color.Green else Color.Gray,
                    )
                )
            }
        }
    }
}
