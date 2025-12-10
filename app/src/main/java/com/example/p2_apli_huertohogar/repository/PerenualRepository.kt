package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.PerenualPlant
import com.example.p2_apli_huertohogar.model.network.PerenualApi
import com.example.p2_apli_huertohogar.model.network.PerenualClient

class PerenualRepository {

    private val api: PerenualApi =
        PerenualClient.retrofit.create(PerenualApi::class.java)

    suspend fun obtenerPlantas(): List<PerenualPlant> {
        val response = api.getPlants(apiKey = API_KEY)
        return response.data
    }

    companion object {

        const val API_KEY = "sk-gsYG693393ca9cc7613845"
    }
}
