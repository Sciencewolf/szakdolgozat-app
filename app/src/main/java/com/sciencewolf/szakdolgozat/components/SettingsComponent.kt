package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

class SettingsComponent {
    @Composable
    fun Settings(navController: NavController) {
        Column {
            Text(text = "Settings", fontSize = 32.sp)
            Spacer(modifier = Modifier)
            TextButton(onClick = { navController.navigate("Home") }) {
                Icon(Icons.Rounded.Close, contentDescription = "Close")
            }
        }
    }
}