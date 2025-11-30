package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.LoginRequest
import com.example.p2_apli_huertohogar.model.LoginResponse
import com.example.p2_apli_huertohogar.model.RegisterRequest
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.AuthApi

class AuthRepository {

    private val api = ApiClient.retrofit.create(AuthApi::class.java)

    suspend fun login(email: String, password: String): LoginResponse {
        val request = LoginRequest(email, password)
        return api.login(request)
    }
    suspend fun registro(nombre: String, email: String, password: String): LoginResponse {
        val request = RegisterRequest(nombre, email, password)
        return api.register(request)
    }
}