package com.sciencewolf.szakdolgozat.pages.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import io.github.jan.supabase.SupabaseClient

open class SettingsPage {
    private val whatsNewComponent = WhatsNewComponent()
    private val shareAppComponent = ShareAppComponent()
    private val copyrightComponent = CopyrightComponent()
    private val showAppVersionComponent = GetVersionComponent()

    @Composable
    fun LoadSettingsPage(
        modifier: Modifier = Modifier,
        supabase: SupabaseClient
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Settings",
                    fontSize = 32.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                showAppVersionComponent.GetAndDisplayVersion(supabase = supabase)
            }
            Spacer(modifier = Modifier.padding(vertical = 20.dp))
            Column (
                modifier = Modifier,
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                whatsNewComponent.Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_present_96,
                    text = "What's New",
                    url = "https://github.com/Sciencewolf/szakdolgozat-app/releases"
                )
                shareAppComponent.Content(
                    modifier = Modifier,
                    icon = R.drawable.icons8_share_96,
                    text = "Share this App"
                )
                copyrightComponent.Content(modifier = Modifier)
            }
        }
    }
}