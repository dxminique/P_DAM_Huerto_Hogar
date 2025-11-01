package com.example.p2_apli_huertohogar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.navigation.compose.*
import com.example.p2_apli_huertohogar.ui.screens.*
import com.example.p2_apli_huertohogar.ui.theme.P2_Apli_HuertoHogarTheme
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            P2_Apli_HuertoHogarTheme {
                val navController = rememberNavController()
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

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
                        composable("login") { LoginScreen(navController) }
                        composable("home") { HomeScreen(navController) }
                        composable("registro") { RegistroScreen(navController) }
                        composable("productos") { ProductoScreen(navController) }
                        composable("pedidos") { PedidosScreen(navController) }
                    }
                }
            }
        }
    }
}