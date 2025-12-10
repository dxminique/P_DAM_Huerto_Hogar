package com.example.p2_apli_huertohogar.repository

import com.example.p2_apli_huertohogar.model.Usuario
import com.example.p2_apli_huertohogar.model.network.ApiClient
import com.example.p2_apli_huertohogar.model.network.UsuarioApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

interface UsuarioRepository {
    fun observeAll(): Flow<List<Usuario>>
    suspend fun obtenerPorEmail(email: String): Usuario?
}

class InMemoryUsuarioRepository : UsuarioRepository {

    private val _usuarios = MutableStateFlow(
        listOf(
            Usuario(1, "Dominique", "domi@example.com", "1234", "Casa Central"),
            Usuario(2, "María López", "maria@example.com", "abcd", "Villa Los Jardines 105"),
            Usuario(3, "Juan Pérez", "juan@example.com", "qwerty", "Pasaje Las Rosas 45"),
            Usuario(4, "Carlos Torres", "carlos@example.com", "4567", "Av. Siempre Viva 742"),
            Usuario(5, "Ana Gómez", "ana@example.com", "pass123", "Camino El Bosque 32")
        )
    )

    override fun observeAll(): Flow<List<Usuario>> = _usuarios.asStateFlow()

    override suspend fun obtenerPorEmail(email: String): Usuario? {
        return _usuarios.value.firstOrNull { it.correo == email }
    }
}

class RemoteUsuarioRepository : UsuarioRepository {

    private val api = ApiClient.retrofit.create(UsuarioApi::class.java)
    private val cache = MutableStateFlow<List<Usuario>>(emptyList())

    override fun observeAll(): Flow<List<Usuario>> = cache.asStateFlow()

    override suspend fun obtenerPorEmail(email: String): Usuario? {
        val usuario = api.getUsuarioPorEmail(email)
        cache.value = cache.value.filterNot { it.id == usuario.id } + usuario
        return usuario
    }
}
