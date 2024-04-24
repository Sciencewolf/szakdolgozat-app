package com.sciencewolf.szakdolgozat.routing

sealed class Routes(val route: String) {
    object HOME: Routes("home")
    object IMAGES: Routes("images")
    object CONTROL: Routes("control")
    object SETTINGS: Routes("settings")
}