package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import com.sciencewolf.szakdolgozat.components.SettingsComponent

open class SettingsPage {
    private val settingsComponent = SettingsComponent()

    @Composable
    fun LoadSettings() {
        settingsComponent.Settings()
    }
}