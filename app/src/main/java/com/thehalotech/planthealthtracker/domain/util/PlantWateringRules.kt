package com.thehalotech.planthealthtracker.domain.util

object PlantWateringRules {
    fun mapWatering(moisture: Int): Int {
        return when(moisture) {
            1 -> 7
            2 -> 3
            3 -> 1
            else -> 2
        }
    }
}