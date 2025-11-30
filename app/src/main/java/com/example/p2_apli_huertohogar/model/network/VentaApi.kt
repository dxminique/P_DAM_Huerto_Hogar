package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.Venta
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface VentaApi {

    @GET("api/ventas")
    suspend fun getVentas(): List<Venta>

    @GET("api/ventas/usuario/{email}")
    suspend fun getVentasByUsuario(@Path("email") email: String): List<Venta>

    @POST("api/ventas")
    suspend fun crearVenta(@Body venta: Venta): Venta
}
