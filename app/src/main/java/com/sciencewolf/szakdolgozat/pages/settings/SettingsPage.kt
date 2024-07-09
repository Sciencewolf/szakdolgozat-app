package com.sciencewolf.szakdolgozat.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sciencewolf.szakdolgozat.R

open class SettingsPage {
    private val contentComponent = WhatsNewComponent()
    private val shareAppComponent = ShareAppComponent()
    private val copyrightComponent = CopyrightComponent()

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
                contentComponent.Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_present_96,
                    text = "What's New",
                    url = "https://github.com/Sciencewolf/szakdolgozat-app/releases"
                )
                shareAppComponent.ShareApp(
                    modifier = Modifier,
                    icon = R.drawable.icons8_share_96,
                    text = "Share this App"
                )
                copyrightComponent.Copyright(modifier = Modifier)
            }
        }
    }
}