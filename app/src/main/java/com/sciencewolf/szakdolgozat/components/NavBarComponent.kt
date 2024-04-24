package com.sciencewolf.szakdolgozat.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.sciencewolf.szakdolgozat.routing.FOCUS_ON
import com.sciencewolf.szakdolgozat.R

open class NavBarComponent {

    @Composable
    fun NavBar(
        focusOn: FOCUS_ON,
        navController: NavController
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
                    FOCUS_ON.IMAGES -> ImagesElement(navController)
                    FOCUS_ON.HOME -> HomeElement(navController)
                    FOCUS_ON.CONTROL -> ControlElement(navController)
                }
            }
        }
    }

    @Composable
    fun HomeElement(navController: NavController) {
        TextButton(
            onClick = {
                navController.navigate("images")
        }) {
            Content(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        Button(
            onClick = {
                // do nothing
            }) {
            Content(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        TextButton(
            onClick = {
                navController.navigate("control")
        }) {
            Content(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun ImagesElement(navController: NavController) {
        Button(
            onClick = {
                // do nothing
        }) {
            Content(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        TextButton(
            onClick = {
                navController.navigate("home")
            }) {
            Content(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        TextButton(
            onClick = {
                navController.navigate("control")
        }) {
            Content(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun ControlElement(navController: NavController) {
        TextButton(
            onClick = {
                navController.navigate("images")

        }) {
            Content(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        TextButton(
            onClick = {
                navController.navigate("home")

            }) {
            Content(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        Button(
            onClick = {
                // do nothing
        }) {
            Content(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun SettingsElement(navController: NavController) {
        TextButton(
            onClick = {
                navController.navigate("images")

            }) {
            Content(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        TextButton(
            onClick = {
                navController.navigate("home")

            }) {
            Content(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        TextButton(
            onClick = {
                // do nothing
            }) {
            Content(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }

        Button(onClick = { TODO("settings not yet implemented") }) {

        }
    }

    @Composable
    private fun Content(
        imageId: Int,
        imageContentDesc: String,
        text: String
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(
                    id = imageId
                ),
                contentDescription = "$imageContentDesc icon"
            )
            Text(
                text = text,
                color = Color.Black
            )
        }
    }
}