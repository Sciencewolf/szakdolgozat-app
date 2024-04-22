package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R
import java.time.LocalDateTime

class TopBarComponent {
    @Composable
    fun TopBar() {
        val currentYear by remember {
            mutableStateOf(LocalDateTime.now().year)
        }
        val currentMonth by remember {
            mutableStateOf(LocalDateTime.now().monthValue)
        }
        val currentDay by remember {
            mutableStateOf(LocalDateTime.now().dayOfMonth)
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            // date and time
            Text(
                text = currentMonth.toString()
                    .plus("/")
                    .plus(currentDay.toString())
                    .plus("/")
                    .plus(currentYear.toString())
            )
            // about
            TextButton(onClick = {

            }) {
                Image(
                    painter = painterResource(id = R.drawable.icons8_info_90),
                    contentDescription = "about icon"
                )
            }
        }
    }
}