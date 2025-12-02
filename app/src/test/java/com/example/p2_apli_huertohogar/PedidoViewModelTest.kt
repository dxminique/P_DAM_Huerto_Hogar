package com.example.p2_apli_huertohogar

import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.PedidoRepository
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import java.math.BigDecimal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PedidoViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    @Test
    fun confirmar_ok_actualiza_state_y_vacia_carrito() = runTest {

        val mockRepo = mock<PedidoRepository>()

        val fakeBody = mock<ResponseBody>()

        whenever(mockRepo.confirmarPedido(any()))
            .thenReturn(fakeBody)


        val vm = PedidoViewModel(repository = mockRepo)


        val p1 = Producto(
            id = 1L,
            nombre = "Lechuga",
            descripcion = "Lechuga crocante",
            precio = BigDecimal("990"),
            stock = 10,
            activo = true
        )

        vm.agregarAlCarrito(p1)

        // acción que queremos probar
        vm.confirmarPedido(emailCliente = "demo@correo.cl")
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertTrue(state.success)
        assertNull(state.error)

        assertTrue(vm.carrito.isEmpty())


        verify(mockRepo).confirmarPedido(any())
    }

    @Test
    fun confirmar_error_muestra_mensaje_y_no_vacia_carrito() = runTest {
        val mockRepo = mock<PedidoRepository>()


        whenever(mockRepo.confirmarPedido(any()))
            .thenThrow(RuntimeException("Error al confirmar pedido"))

        val vm = PedidoViewModel(repository = mockRepo)

        val p1 = Producto(
            id = 1L,
            nombre = "Lechuga",
            descripcion = "Lechuga crocante",
            precio = BigDecimal("990"),
            stock = 10,
            activo = true
        )
        vm.agregarAlCarrito(p1)

        vm.confirmarPedido(emailCliente = "demo@correo.cl")
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertFalse(state.success)
        assertNotNull(state.error)
        assertTrue(state.error!!.contains("Error"))

        assertFalse(vm.carrito.isEmpty())

        verify(mockRepo).confirmarPedido(any())
    }
}
