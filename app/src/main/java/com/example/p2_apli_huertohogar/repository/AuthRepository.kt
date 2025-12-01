package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.LoginRequest
import com.example.p2_apli_huertohogar.model.RegisterRequest
import com.example.p2_apli_huertohogar.model.network.AuthApi

class AuthRepository {

    private val api = AuthApi.instance

    suspend fun login(request: LoginRequest) =
        api.login(request)

    suspend fun registrar(request: RegisterRequest) =
        api.registrar(request)
}
