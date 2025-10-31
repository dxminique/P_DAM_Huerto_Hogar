package com.example.p2_apli_huertohogar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p2_apli_huertohogar.model.Usuario
import com.example.p2_apli_huertohogar.repository.UsuarioRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class UsuarioUiState(
    val usuarios: List<Usuario> = emptyList()
)

class UsuarioViewModel(
    private val repo: UsuarioRepository
) : ViewModel() {

    val uiState: StateFlow<UsuarioUiState> =
        repo.observeAll()
            .map { list -> UsuarioUiState(usuarios = list) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                UsuarioUiState()
            )
}