package com.sciencewolf.szakdolgozat.routing

sealed class Routes(val route: String) {
    data object HOME: Routes("Home")
    data object IMAGES: Routes("Images")
    data object CONTROL: Routes("Control")
    data object PROFILE: Routes("Profile")
    data object SETTINGS: Routes("Settings")
}