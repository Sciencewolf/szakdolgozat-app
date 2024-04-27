package com.sciencewolf.szakdolgozat.pages

import androidx.compose.runtime.Composable
import com.sciencewolf.szakdolgozat.components.profilecomponents.ProfileComponent

open class ProfilePage {
    private val profileComponent = ProfileComponent()

    @Composable
    fun LoadProfile() {
        profileComponent.Profile()
    }
}