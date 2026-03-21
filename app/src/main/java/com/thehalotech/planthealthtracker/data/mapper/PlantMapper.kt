package com.thehalotech.planthealthtracker.data.mapper

import androidx.room.PrimaryKey
import com.thehalotech.planthealthtracker.data.local.PlantEntity
import com.thehalotech.planthealthtracker.data.remote.dto.DetailsResponse
import com.thehalotech.planthealthtracker.data.remote.dto.IdentifiedPlant
import com.thehalotech.planthealthtracker.data.remote.dto.PlantIdResponse
import com.thehalotech.planthealthtracker.domain.model.IdentifiedPlantDomain
import com.thehalotech.planthealthtracker.domain.model.Plant
import com.thehalotech.planthealthtracker.domain.model.PlantDetailsDomain
import com.thehalotech.planthealthtracker.domain.util.PlantWateringRules
import kotlin.String


fun PlantIdResponse.toDomain(): List<IdentifiedPlantDomain> {
    return entities.map { it.toDomain() }
}

fun IdentifiedPlant.toDomain(): IdentifiedPlantDomain {
    return IdentifiedPlantDomain(
        accessToken = accessToken,
        entityName = entityName
    )
}

fun DetailsResponse.toDomain(): PlantDetailsDomain {
    val moisture = watering?.min ?: 2
    return PlantDetailsDomain(
        id = entityId,
        name = name,
        description = description.value,
        wateringFreq = PlantWateringRules.mapWatering(moisture),
        commonName = commonNames.firstOrNull() ?: "",
        lightRequirement = bestLightCondition)
}

fun Plant.toEntity(imagePath: String?): PlantEntity {
    return PlantEntity(
        commonName = commonName,
        wateringFrequency = wateringFrequency.toString(),
        lastWatered = lastWatered,
        lightRequirement = lightRequirement,
        description = description,
        imagePath = imagePath,
        plantName = name,
        entityId = id
    )
}

fun PlantEntity.toDomain(): Plant {
    return Plant(
        id = entityId,
        name = plantName,
        commonName = commonName,
        wateringFrequency = wateringFrequency.toInt(),
        lastWatered = lastWatered,
        lightRequirement = lightRequirement,
        description = description,
        imagePath = imagePath
    )
}


