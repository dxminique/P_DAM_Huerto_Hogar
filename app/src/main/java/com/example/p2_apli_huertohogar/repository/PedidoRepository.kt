package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.CrearPedidoDTO
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.PedidoApi
import retrofit2.HttpException

class PedidoRepository {

    private val api = ApiClient.retrofit.create(PedidoApi::class.java)

    suspend fun confirmarPedido(dto: CrearPedidoDTO) {
        val response = api.confirmarPedido(dto)
        if (!response.isSuccessful) {
            throw HttpException(response)
        }
    }
}
