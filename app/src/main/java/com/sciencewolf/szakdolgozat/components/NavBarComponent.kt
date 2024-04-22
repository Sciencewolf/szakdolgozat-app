package com.sciencewolf.szakdolgozat.components

import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.sciencewolf.szakdolgozat.FOCUS_ON
import com.sciencewolf.szakdolgozat.R

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
        Button(
            onClick = {

            }) {
            ColumnItems(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun ImagesElement() {
        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        Button(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }

        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun ControlElement() {
        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_home_90,
                imageContentDesc = "home",
                text = "Home"
            )
        }

        TextButton(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_image_90,
                imageContentDesc = "images",
                text = "Images"
            )
        }
        Button(onClick = {

        }) {
            ColumnItems(
                imageId = R.drawable.icons8_control_90,
                imageContentDesc = "control",
                text = "Control"
            )
        }
    }

    @Composable
    fun ColumnItems(
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