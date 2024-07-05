package com.sciencewolf.szakdolgozat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    vertical = 5.dp,
                    horizontal = 5.dp
                ),
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
                    FOCUS_ON.HOME -> HomeElement(navController)
                    FOCUS_ON.CONTROL -> ControlElement(navController)
                }
            }
        }
    }

    @Composable
    fun HomeElement(navController: NavController) {
        ItemUnit(
            navController = navController,
            imageId = R.drawable.icons8_home_90,
            text = "Home",
            isNavigate = false
        )

        ItemUnit(
            navController = navController,
            imageId = R.drawable.icons8_control_90,
            text = "Control",
            isNavigate = true
        )
    }

    @Composable
    fun ControlElement(navController: NavController) {
        ItemUnit(
            navController = navController,
            imageId = R.drawable.icons8_home_90,
            text = "Home",
            isNavigate = true
        )

        ItemUnit(
            navController = navController,
            imageId = R.drawable.icons8_control_90,
            text = "Control",
            isNavigate = false
        )
    }

    @Composable
    private fun ItemUnit(
        navController: NavController,
        imageId: Int,
        text: String,
        isNavigate: Boolean
    ) {
        val col = if(isNavigate) Color.Transparent else Color.DarkGray
        val size = 72.dp
        TextButton(
            onClick = {
                if(isNavigate) navController.navigate(text)

            },
            colors = ButtonDefaults.buttonColors(col),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.size(width = size, height = size)
        ) {
            Content(
                imageId = imageId,
                imageContentDesc = text,
                text = text
            )
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
                color = Color.White
            )
        }
    }
}