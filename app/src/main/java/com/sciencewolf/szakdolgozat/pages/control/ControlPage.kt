package com.sciencewolf.szakdolgozat.pages.control

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R

open class ControlPage {
    private val controlRaspberryPi = ControlRaspberryPi()

    @Composable
    fun LoadControlPage(
        modifier: Modifier = Modifier
    ) {
        var switchStateCooler by remember { mutableStateOf(false) }
        var switchStateHeatingElement by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ControlRow(
                label = stringResource(R.string.cooler_text),
                switchState = switchStateCooler,
                onSwitchChange = { switchStateCooler = it }
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            ControlRow(
                label = stringResource(R.string.heating_element_text),
                switchState = switchStateHeatingElement,
                onSwitchChange = { switchStateHeatingElement = it },
                width = 200.dp
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            controlRaspberryPi.Cooler(on=switchStateCooler)
            controlRaspberryPi.HeatingElement(on=switchStateHeatingElement)
            Column {
                controlRaspberryPi.GetLidStatus()
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
        height: Dp = 50.dp
    ) {
        Card(
            modifier = Modifier.background(Color.hsl(227f, 0.5f, 0.27f))
        ) {
            Row(
                modifier = Modifier
                    .size(width = width, height = height)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = label)
                Switch(
                    checked = switchState,
                    onCheckedChange = onSwitchChange,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                    )
                )
            }
        }
    }
}
