package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import com.sciencewolf.szakdolgozat.components.ProfileComponent

open class ProfilePage {
    private val settingsComponent = ProfileComponent()

    @Composable
    fun LoadProfile() {
        settingsComponent.Profile()
    }
}