package com.example.p2_apli_huertohogar.model.network

import com.example.p2_apli_huertohogar.model.Producto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoApi {

    @GET("api/productos")
    suspend fun getProductos(): List<Producto>

    @GET("api/productos/{id}")
    suspend fun getProductoById(@Path("id") id: Long): Producto
}