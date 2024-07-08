package com.sciencewolf.szakdolgozat.pages

import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.R

open class SettingsPage {

    @Composable
    fun LoadSettingsPage(modifier: Modifier = Modifier) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Settings",
                fontSize = 32.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Column (
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_present_96,
                    text = "What's New",
                    url = "https://github.com/Sciencewolf/szakdolgozat-app/releases/tag/v2024.7.6"
                )
                Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_share_96,
                    text = "Share this App",
                    url = "https://hippo-immense-plainly.ngrok-free.app/"
                )
                Copyright(modifier = Modifier)
            }
        }
    }

    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        icon: Int,
        text: String,
        url: String
    ) {
        val context = LocalContext.current
        val intent = remember {
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        }

        Row (
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "$text icon",
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = {
                    context.startActivity(intent)
                }, contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = text,
                        Modifier.background(Color.Transparent),
                        color = Color.White
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.icons8_arrow_right_60),
                    contentDescription = "arrow right icon"
                )
            }
        }
        Divider(modifier = Modifier.padding(8.dp))
    }

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