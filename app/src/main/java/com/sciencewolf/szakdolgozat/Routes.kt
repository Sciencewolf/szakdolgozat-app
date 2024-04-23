package com.sciencewolf.szakdolgozat

sealed class Routes(val route: String) {
    object HOME: Routes("home")
    object IMAGES: Routes("images")
    object CONTROL: Routes("control")
}