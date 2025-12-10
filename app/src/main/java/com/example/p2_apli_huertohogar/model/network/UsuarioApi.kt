package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.Usuario
import retrofit2.http.GET
import retrofit2.http.Query

interface UsuarioApi {

    @GET("api/usuarios/por-email")
    suspend fun getUsuarioPorEmail(
        @Query("email") email: String
    ): Usuario
}
