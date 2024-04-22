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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sciencewolf.szakdolgozat.R
import java.time.LocalDateTime

open class ExperimentalComponents {

    @Composable
    fun SwitchBetweenThemes(
        modifier: Modifier = Modifier
    ) {
        var change by remember {
            mutableStateOf(true)
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            if (change) {
                TextButton(onClick = {
                    change = !change
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_light_theme_90),
                        contentDescription = "light theme icon"
                    )
                }
            }else {
                TextButton(onClick = {
                    change = !change
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.icons8_dark_theme_90),
                        contentDescription = "dark theme icon"
                    )
                }
            }
        }
    }

}