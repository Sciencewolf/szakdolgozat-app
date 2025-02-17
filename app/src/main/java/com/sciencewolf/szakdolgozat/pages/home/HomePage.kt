package com.sciencewolf.szakdolgozat.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

open class HomePage {
    private val welcomeBackCardComponent = WelcomeBackCardComponent()

    @Composable
    fun LoadHomePage() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            welcomeBackCardComponent.ShowCard()
            Divider()
            Column(modifier = Modifier.padding(vertical = 8.dp)
                .background(Color.Black)
                .size(width = 300.dp, height = 200.dp)
            ) {
                Text(text = "Graph")
            }
            Divider()
            Column(modifier = Modifier.padding(vertical = 8.dp)
                .background(Color.Green).size(width = 300.dp, height = 150.dp)) {
                Text(text = "Events", color = Color.Black)
            }

        }
    }
}