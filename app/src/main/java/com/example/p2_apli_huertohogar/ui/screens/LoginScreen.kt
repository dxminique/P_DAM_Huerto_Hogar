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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.p2_apli_huertohogar.viewModel.LoginNavEvent
import com.example.p2_apli_huertohogar.viewModel.LoginViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel() 
) {
   
    val uiState by viewModel.uiState.collectAsState()

   
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is LoginNavEvent.NavigateToHome -> {
                    navController.navigate(event.route) {
                       
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

            
            if (uiState.errorMensaje != null) {
                Text(
                    text = uiState.errorMensaje!!, 
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            OutlinedTextField(
                value = uiState.correo, 
                onValueChange = { viewModel.onCorreoChange(it) },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null 
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = uiState.contrasena, 
                onValueChange = { viewModel.onContrasenaChange(it) }, 
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.errorMensaje != null 
            )

            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { viewModel.onLoginClick() }, 
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
