package com.example.p2_apli_huertohogar

import com.example.p2_apli_huertohogar.model.LoginRequest
import com.example.p2_apli_huertohogar.model.LoginResponse
import com.example.p2_apli_huertohogar.model.RegisterRequest
import com.example.p2_apli_huertohogar.model.Usuario
import com.example.p2_apli_huertohogar.repository.AuthRepository
import com.example.p2_apli_huertohogar.viewModel.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()


    @Test
    fun login_ok() = runTest {
        val repo = mock<AuthRepository>()

        // Tu backend devuelve SOLO token, así que respetamos eso.
        val fakeResponse = LoginResponse(
            token = "FAKE_TOKEN"
        )

        // login(request: LoginRequest): LoginResponse
        whenever(repo.login(any<LoginRequest>()))
            .thenReturn(fakeResponse)

        val vm = AuthViewModel(repository = repo)

        vm.login("demo@correo.cl", "123456")
        advanceUntilIdle()

        val state = vm.uiState

        // No dependemos de campos que NO existen en LoginResponse
        assertTrue(state.isLoggedIn)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }


    @Test
    fun login_error() = runTest {
        val repo = mock<AuthRepository>()

        whenever(repo.login(any<LoginRequest>()))
            .thenThrow(RuntimeException("Credenciales inválidas"))

        val vm = AuthViewModel(repository = repo)

        vm.login("mal@correo.cl", "xxxx")
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoggedIn)
        assertFalse(state.isLoading)
        assertNotNull(state.error)
    }


    @Test
    fun registro_ok() = runTest {
        val repo = mock<AuthRepository>()

        val fakeUsuario = Usuario(
            id = 1,
            nombre = "Angela",
            correo = "angelaHU@gmail.com",
            contrasena = "123456",
            direccion = "Santiago"
        )

        // registrar(request: RegisterRequest): Usuario
        whenever(repo.registrar(any<RegisterRequest>()))
            .thenReturn(fakeUsuario)

        val vm = AuthViewModel(repository = repo)

        vm.registrar(
            nombre = "Angela",
            email = "angelaHU@gmail.com",
            password = "123456"
        )
        advanceUntilIdle()

        val state = vm.uiState

        assertTrue(state.isRegistered)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }
}
