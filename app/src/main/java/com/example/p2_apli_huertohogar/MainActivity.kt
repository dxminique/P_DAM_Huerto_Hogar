package com.example.p2_apli_huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.p2_apli_huertohogar.ui.screens.BottomNavBar
import com.example.p2_apli_huertohogar.ui.screens.CamaraScreen
import com.example.p2_apli_huertohogar.ui.screens.CarritoScreen
import com.example.p2_apli_huertohogar.ui.screens.HomeScreen
import com.example.p2_apli_huertohogar.ui.screens.LoginScreen
import com.example.p2_apli_huertohogar.ui.screens.PerfilScreen
import com.example.p2_apli_huertohogar.ui.screens.ProductoScreen
import com.example.p2_apli_huertohogar.ui.screens.RegistroScreen
import com.example.p2_apli_huertohogar.ui.theme.P2_Apli_HuertoHogarTheme
import com.example.p2_apli_huertohogar.viewModel.AuthViewModel
import com.example.p2_apli_huertohogar.viewModel.PedidoViewModel
import com.example.p2_apli_huertohogar.viewModel.ProductoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2_Apli_HuertoHogarTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

                val authViewModel: AuthViewModel = viewModel()
                val pedidoViewModel: PedidoViewModel = viewModel()
                val productoViewModel: ProductoViewModel = viewModel()

                Scaffold(
                    bottomBar = {
                        if (currentRoute !in listOf("login", "registro")) {
                            BottomNavBar(navController)
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") {
                            LoginScreen(navController, authViewModel)
                        }
                        composable("home") {
                            HomeScreen(navController)
                        }
                        composable("registro") {
                            RegistroScreen(navController, authViewModel)
                        }
                        composable("productos") {
                            ProductoScreen(navController, pedidoViewModel, productoViewModel)
                        }
                        composable("carrito") {
                            CarritoScreen(navController, pedidoViewModel, authViewModel, productoViewModel)
                        }
                        composable("perfil") {
                            PerfilScreen(navController, authViewModel)
                        }
                        composable("camara") {
                            CamaraScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
