package com.sciencewolf.szakdolgozat.components.profilecomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

open class ProfileComponent {
    @Composable
    fun Profile() {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "John Doe",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.padding(horizontal = 10.dp))
                Icon(
                    Icons.Rounded.AccountCircle,
                    contentDescription = "Profile image"
                )
            }
            Spacer(modifier = Modifier)
            Column {
                TextButton(onClick = {

                }) {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier)
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        text = "Log out",
                        color = Color.Red,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}