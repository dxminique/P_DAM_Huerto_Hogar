package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.VentaViewModel

@Composable
fun PedidosScreen(
    navController: NavHostController,
    ventaViewModel: VentaViewModel = viewModel()

) {
    val state = ventaViewModel.uiState

    LaunchedEffect(Unit) {
        ventaViewModel.cargarVentas()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.error != null -> {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }

            else -> {
                LazyColumn {
                    items(state.ventas) { venta ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(Modifier.padding(12.dp)) {
                                Text("Venta ID: ${venta.id}")
                                Text("Cliente: ${venta.cliente}")
                                Text("Fecha: ${venta.fechaVenta}")
                                Text("Total: ${venta.total}")
                                Text("Pedido origen: ${venta.pedidoId}")
                                Text(if (venta.activo) "Activa" else "Inactiva")
                            }
                        }
                    }
                }
            }
        }
    }
}
