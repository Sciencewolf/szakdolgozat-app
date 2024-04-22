package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.sciencewolf.szakdolgozat.FOCUS_ON
import com.sciencewolf.szakdolgozat.R

// refactor: for inside function add params to update text value
open class NavBarComponent {

    @Composable
    fun NavBar(
        focusOn: FOCUS_ON
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                when (focusOn) {
                    FOCUS_ON.HOME -> HomeElement()
                    FOCUS_ON.IMAGES -> ImagesElement()
                    FOCUS_ON.CONTROL -> ControlElement()
                }
            }
        }
    }

    @Composable
    fun HomeElement() {
        OutlinedButton(onClick = {

        }) {
            HomeElementInside()
        }

        TextButton(onClick = {

        }) {
            ImagesElementInside()
        }

        TextButton(onClick = {

        }) {
            ControlElementInside()
        }
    }

    @Composable
    fun ImagesElement() {
        TextButton(onClick = {

        }) {
            HomeElementInside()
        }

        OutlinedButton(onClick = {

        }) {
            ImagesElementInside()
        }

        TextButton(onClick = {

        }) {
            ControlElementInside()
        }
    }

    @Composable
    fun ControlElement() {
        TextButton(onClick = {

        }) {
            HomeElementInside()
        }

        TextButton(onClick = {

        }) {
            ImagesElementInside()
        }

        OutlinedButton(onClick = {

        }) {
            ControlElementInside()
        }
    }

    @Composable
    fun ControlElementInside() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons8_control_90),
                contentDescription = "control icon"
            )
            Text(
                text = "Control",
                color = Color.Black
            )
        }
    }

    @Composable
    fun ImagesElementInside() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.icons8_image_90),
                contentDescription = "images icon"
            )
            Text(
                text = "Images",
                color = Color.Black
            )
        }
    }

    @Composable
    fun HomeElementInside() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = R.drawable.icons8_home_90),
                contentDescription = "home icon")
            Text(
                text = "Home",
                color = Color.Black
            )
        }
    }
}