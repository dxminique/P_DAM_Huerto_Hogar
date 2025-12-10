package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.PerenualListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PerenualApi {

    @GET("species-list")
    suspend fun getPlants(
        @Query("key") apiKey: String,
        @Query("page") page: Int = 1
    ): PerenualListResponse
}
