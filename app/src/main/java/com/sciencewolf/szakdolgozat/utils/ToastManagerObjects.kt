package com.sciencewolf.szakdolgozat.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object SensorOfflineToastManager {
    var toast by mutableStateOf(false)
}

object LidSensorOfflineToastManager {
    var toast by mutableStateOf(false)
}

object CoolerOfflineToastManager {
    var toast by mutableStateOf(false)
}

object HeatingElementOfflineToastManager {
    var toast by mutableStateOf(false)
}

object OtherOfflineToastManager {
    var toast by mutableStateOf(false)
}

object RotateEggsOfflineToastManager {
    var toast by mutableStateOf(false)
}