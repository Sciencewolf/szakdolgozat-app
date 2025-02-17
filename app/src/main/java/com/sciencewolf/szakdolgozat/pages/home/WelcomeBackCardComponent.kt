package com.sciencewolf.szakdolgozat.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.R
import com.sciencewolf.szakdolgozat.pages.control.ControlRaspberryPi
import com.sciencewolf.szakdolgozat.ui.theme.CardLightBlueColor

open class WelcomeBackCardComponent {
    private val controlRaspberryPi = ControlRaspberryPi()

    @Composable
    fun ShowCard(modifier: Modifier = Modifier) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = CardLightBlueColor
            ),
            modifier = Modifier.size(width = 380.dp, height = 300.dp)
        ) {
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
                        text = stringResource(R.string.welcome_back_label),
                        fontSize = 30.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                }
                Spacer(modifier = Modifier)
                controlRaspberryPi.GetTemperatureAndHumiditySensor()
                Spacer(modifier = Modifier)
                controlRaspberryPi.GetLidStatus()
            }
        }
    }
}