package com.sciencewolf.szakdolgozat.pages.control

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R

open class ControlPage {
    private val controlRaspberryPi = ControlRaspberryPi()

    @Composable
    fun LoadControlPage(
        modifier: Modifier = Modifier
    ) {
        var switchStateRed by remember { mutableStateOf(false) }
        var switchStateGreen by remember { mutableStateOf(false) }
        var switchStateBlue by remember { mutableStateOf(false) }
        var switchStateAll by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ControlRow(
                label = stringResource(R.string.red_led),
                switchState = switchStateRed,
                onSwitchChange = { switchStateRed = it }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ControlRow(
                label = stringResource(R.string.green_led),
                switchState = switchStateGreen,
                onSwitchChange = { switchStateGreen = it }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ControlRow(
                label = stringResource(R.string.blue_led),
                switchState = switchStateBlue,
                onSwitchChange = { switchStateBlue = it }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            ControlRow(
                label = stringResource(R.string.all_led),
                switchState = switchStateAll,
                onSwitchChange = { switchStateAll = it }
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))

            controlRaspberryPi.ControlLed(ledColor = "Red", on = switchStateRed)
            controlRaspberryPi.ControlLed(ledColor = "Green", on = switchStateGreen)
            controlRaspberryPi.ControlLed(ledColor = "Blue", on = switchStateBlue)
            controlRaspberryPi.ControlLed(ledColor = "All", on = switchStateAll)
        }
    }

    @Composable
    private fun ControlRow(
        label: String,
        switchState: Boolean,
        onSwitchChange: (Boolean) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = label)
            Switch(
                checked = switchState,
                onCheckedChange = onSwitchChange
            )
        }
    }
}
