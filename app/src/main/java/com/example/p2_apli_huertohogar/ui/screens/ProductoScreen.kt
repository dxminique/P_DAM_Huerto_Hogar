package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel

@Composable
fun ProductoScreen(
    navController: NavHostController,
    pedidoViewModel: PedidoViewModel,
    productoViewModel: ProductoViewModel = viewModel()
) {
    val state = productoViewModel.uiState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Productos",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

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
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.productos) { p ->
                        val disponible = p.stock > 0

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = p.nombre,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(text = p.descripcion)
                                Text(text = "Precio: ${p.precio}")
                                Text(text = "Stock: ${p.stock}")

                                Text(
                                    text = if (disponible) "Disponible" else "Agotado",
                                    color = if (disponible)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.error,
                                    modifier = Modifier.padding(top = 4.dp)
                                )

                                Button(
                                    onClick = { pedidoViewModel.agregarAlCarrito(p) },
                                    enabled = disponible,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Text(
                                        if (disponible)
                                            "Agregar al carrito"
                                        else
                                            "Sin stock"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
