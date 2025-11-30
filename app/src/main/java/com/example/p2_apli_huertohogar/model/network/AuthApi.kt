package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.LoginRequest
import com.example.p2_apli_huertohogar.model.LoginResponse
import com.example.p2_apli_huertohogar.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/registro")
    suspend fun register(@Body request: RegisterRequest): LoginResponse
}