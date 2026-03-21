package com.thehalotech.planthealthtracker.presentation.util

fun getMoistureLevel(wateringFrequency: Int): Int {
    return when (wateringFrequency) {
        1 -> 80
        2 -> 60
        3 -> 40
        7 -> 20
        else -> 50
    }
}