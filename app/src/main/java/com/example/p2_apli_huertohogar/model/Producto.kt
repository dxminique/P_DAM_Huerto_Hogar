package com.example.p2_apli_huertohogar.model
import java.math.BigDecimal

data class Producto(
    val id: Long,
    val nombre: String,
    val descripcion: String,
    val precio: BigDecimal,
    val stock: Int,
    val activo: Boolean
) {}