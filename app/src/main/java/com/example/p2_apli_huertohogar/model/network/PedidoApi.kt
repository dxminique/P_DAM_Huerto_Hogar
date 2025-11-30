package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.Pedido
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PedidoApi {

    @GET("api/pedidos")
    suspend fun getPedidos(): List<Pedido>

    @GET("api/pedidos/{id}")
    suspend fun getPedidoById(@Path("id") id: Long): Pedido

    @POST("api/pedidos")
    suspend fun crearPedido(@Body pedido: Pedido): Pedido
}
