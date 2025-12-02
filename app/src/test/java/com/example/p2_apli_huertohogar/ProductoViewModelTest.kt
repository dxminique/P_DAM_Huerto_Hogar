package com.example.p2_apli_huertohogar

import com.example.p2_apli_huertohogar.model.Producto
import com.example.p2_apli_huertohogar.repository.ProductoRepository
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.math.BigDecimal

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    @Test
    fun productos_ok() = runTest {
        val mockRepo = mock<ProductoRepository>()

        val fakeProductos = listOf(
            Producto(
                id = 1L,
                nombre = "Lechuga Hidropónica",
                descripcion = "Lechuga crocante cultivada sin pesticidas.",
                precio = BigDecimal("990.0"),
                stock = 29,
                activo = true
            ),
            Producto(
                id = 2L,
                nombre = "Zanahoria Orgánica",
                descripcion = "Zanahorias frescas, cultivadas de forma natural.",
                precio = BigDecimal("1290.0"),
                stock = 60,
                activo = true
            )
        )


        whenever(mockRepo.getProductos())
            .thenReturn(fakeProductos)

        val vm = ProductoViewModel(repository = mockRepo)

        vm.cargarProductos()
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(2, state.productos.size)
        assertEquals("Lechuga Hidropónica", state.productos[0].nombre)
    }

    @Test
    fun productos_error() = runTest {
        val mockRepo = mock<ProductoRepository>()

        whenever(mockRepo.getProductos())
            .thenThrow(RuntimeException("Error al cargar productos"))

        val vm = ProductoViewModel(repository = mockRepo)

        vm.cargarProductos()
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertNotNull(state.error)
        assertTrue(state.productos.isEmpty())
    }
}
