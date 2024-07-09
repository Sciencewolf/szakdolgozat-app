package com.sciencewolf.szakdolgozat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sciencewolf.szakdolgozat.utils.FOCUS_ON

open class NavBarComponent {

    @Composable
    fun NavBar(
        focusOn: FOCUS_ON,
        navController: NavController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 5.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                when (focusOn) {
                    FOCUS_ON.SETTINGS -> SettingsElement(navController)
                    FOCUS_ON.HOME -> HomeElement(navController)
                    FOCUS_ON.CONTROL -> ControlElement(navController)
                }
            }
        }
    }

    @Composable
    fun SettingsElement(navController: NavController) {
        Content(
            navController = navController,
            imageId = R.drawable.icons8_settings_60,
            text = "Settings",
            isNavigate = false
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_home_60,
            text = "Home",
            isNavigate = true
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_control_60,
            text = "Control",
            isNavigate = true
        )
    }

    @Composable
    private fun HomeElement(navController: NavController) {
        Content(
            navController = navController,
            imageId = R.drawable.icons8_settings_60,
            text = "Settings",
            isNavigate = true
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_home_60,
            text = "Home",
            isNavigate = false
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_control_60,
            text = "Control",
            isNavigate = true
        )
    }

    @Composable
    private fun ControlElement(navController: NavController) {
        Content(
            navController = navController,
            imageId = R.drawable.icons8_settings_60,
            text = "Settings",
            isNavigate = true
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_home_60,
            text = "Home",
            isNavigate = true
        )

        Content(
            navController = navController,
            imageId = R.drawable.icons8_control_60,
            text = "Control",
            isNavigate = false
        )
    }

    @Composable
    private fun Content(
        navController: NavController,
        imageId: Int,
        text: String,
        isNavigate: Boolean
    ) {
        val buttonColor = if(isNavigate) Color.Transparent else Color.DarkGray
        val size = 70.dp
        TextButton(
            onClick = { if(isNavigate) navController.navigate(text) },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(buttonColor),
            modifier = Modifier.size(width = size, height = size)
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(
                        id = imageId
                    ),
                    contentDescription = "$text icon"
                )
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }
}