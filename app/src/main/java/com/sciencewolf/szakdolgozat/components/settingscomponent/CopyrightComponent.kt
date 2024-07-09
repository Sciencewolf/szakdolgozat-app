package com.sciencewolf.szakdolgozat.components.settingscomponent

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

open class CopyrightComponent {

    @Composable
    fun Copyright(modifier: Modifier = Modifier) {
        val context = LocalContext.current
        val intent = remember {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Sciencewolf")
            )
        }

        Row (
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 85.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Row (
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Szakdolgozat App by",
                    modifier = Modifier.padding(horizontal = 4.dp),
                    color = Color.Gray,
                    fontSize = 14.sp
                )
                TextButton(onClick = {
                    context.startActivity(intent)
                }, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = "Márton Áron",
                        color = Color.Gray,
                        modifier = Modifier.background(Color.Transparent),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}