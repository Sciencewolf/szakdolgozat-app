package com.sciencewolf.szakdolgozat

sealed class Routes(val route: String) {
    object HOME: Routes(FOCUS_ON.HOME.toString())
    object IMAGES: Routes(FOCUS_ON.IMAGES.toString())
    object CONTROL: Routes(FOCUS_ON.CONTROL.toString())
}