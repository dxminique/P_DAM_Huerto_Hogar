package com.example.p2_apli_huertohogar.model

import com.google.gson.annotations.SerializedName

data class PerenualListResponse(
    @SerializedName("data")
    val data: List<PerenualPlant>
)

data class PerenualPlant(
    val id: Int,

    @SerializedName("common_name")
    val commonName: String?,


    @SerializedName("scientific_name")
    val scientificName: List<String>?,

    val cycle: String?,


    val watering: String?,


    val sunlight: List<String>?
)
