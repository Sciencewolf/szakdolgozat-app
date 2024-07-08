package com.sciencewolf.szakdolgozat.routing

sealed class Routes(val route: String) {
    data object HOME: Routes("Home")
    data object CONTROL: Routes("Control")
    data object SETTINGS: Routes("Settings")
}