package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.p2_apli_huertohogar.viewModel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    val state = authViewModel.uiState

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    if (state.isLoggedIn) {
        LaunchedEffect(key1 = state.isLoggedIn) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEFFBF3)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Iniciar sesión",
                style = MaterialTheme.typography.headlineMedium,
                color = Color(0xFF1B5E20),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )

            if (state.error != null) {
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Button(
                onClick = { authViewModel.login(email, password) },
                enabled = !state.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(4.dp),
                        strokeWidth = 2.dp,
                        color = Color.White
                    )
                } else {
                    Text("Iniciar sesión")
                }
            }

            Button(
                onClick = { navController.navigate("registro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF81C784),
                    contentColor = Color.White
                )
            ) {
                Text("Crear cuenta")
            }
        }
    }
}
