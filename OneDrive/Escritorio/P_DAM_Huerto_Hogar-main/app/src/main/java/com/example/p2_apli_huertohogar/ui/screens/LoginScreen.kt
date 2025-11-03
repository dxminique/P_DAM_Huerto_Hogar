package com.example.p2_apli_huertohogar.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // <-- Importa esto
import com.example.p2_apli_huertohogar.viewModel.LoginNavEvent
import com.example.p2_apli_huertohogar.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel() // <-- 1. Obtén la instancia del ViewModel
) {
    // 2. Observa el estado del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // 3. Escucha los eventos de navegación (para navegar SÓLO cuando el VM lo ordene)
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is LoginNavEvent.NavigateToHome -> {
                    navController.navigate(event.route) {
                        // Limpia el stack para que no pueda volver al login con el botón "atrás"
                        popUpTo("login") { inclusive = true }
                    }
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFBFF886)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Iniciar sesión", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(20.dp))

            // 4. Muestra el mensaje de error si existe
            if (uiState.errorMensaje != null) {
                Text(
                    text = uiState.errorMensaje!!, // <-- El mensaje que pusiste en el VM
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            OutlinedTextField(
                value = uiState.correo, // <-- Usa el estado del VM
                onValueChange = { viewModel.onCorreoChange(it) }, // <-- Llama al VM
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null // <-- Resalta el campo si hay error
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = uiState.contrasena, // <-- Usa el estado del VM
                onValueChange = { viewModel.onContrasenaChange(it) }, // <-- Llama al VM
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null // <-- Resalta el campo si hay error
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { viewModel.onLoginClick() }, // <-- 5. Llama a la lógica de validación
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Entrar")
            }
            Spacer(modifier = Modifier.height(10.dp))
            TextButton(onClick = { navController.navigate("registro") }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}