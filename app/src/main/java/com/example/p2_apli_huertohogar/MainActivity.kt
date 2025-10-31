package com.example.p2_apli_huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.p2_apli_huertohogar.repository.InMemoryPedidoRepository
import com.example.p2_apli_huertohogar.repository.InMemoryProductoRepository
import com.example.p2_apli_huertohogar.repository.InMemoryUsuarioRepository
import com.example.p2_apli_huertohogar.ui.screens.HomeScreen
import com.example.p2_apli_huertohogar.ui.screens.PedidosScreen
import com.example.p2_apli_huertohogar.ui.screens.ProductoScreen
import com.example.p2_apli_huertohogar.ui.theme.P2_Apli_HuertoHogarTheme
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel
import com.example.p2_apli_huertohogar.viewModel.UsuarioViewModel
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2_Apli_HuertoHogarTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") {
                            HomeScreen(navController = navController)
                        }
                        composable("productos") {
                            ProductoScreen(navController)
                        }
                        composable("pedidos")
                        { PedidosScreen(navController) }


                    }
                }
            }
        }
    }
}