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

@OptIn(ExperimentalCoroutinesApi::class)
class ProductoViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    // ---------------------------
    // PRODUCTOS OK
    // ---------------------------
    @Test
    fun productos_ok() = runTest {
        val mockRepo = mock<ProductoRepository>()

        val fakeList = listOf(
            Producto(id = 1, nombre = "Lechuga", descripcion = "Verde", precio = 990.0, stock = 10),
            Producto(id = 2, nombre = "Zanahoria", descripcion = "Naranja", precio = 1200.0, stock = 5)
        )

        whenever(mockRepo.obtenerProductos()).thenReturn(fakeList)

        val vm = ProductoViewModel(repository = mockRepo)

        vm.cargarProductos()
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertNull(state.error)
        assertEquals(2, state.productos.size)
        assertEquals("Lechuga", state.productos[0].nombre)
    }


    @Test
    fun productos_error() = runTest {
        val mockRepo = mock<ProductoRepository>()

        whenever(mockRepo.obtenerProductos()).thenThrow(RuntimeException("Error al cargar productos"))

        val vm = ProductoViewModel(repository = mockRepo)

        vm.cargarProductos()
        advanceUntilIdle()

        val state = vm.uiState

        assertFalse(state.isLoading)
        assertNotNull(state.error)
        assertEquals(0, state.productos.size)
    }
}
