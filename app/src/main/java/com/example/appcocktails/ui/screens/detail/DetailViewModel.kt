package com.example.appcocktails.ui.screens.detail

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcocktails.data.local.CocktailDatabase
import com.example.appcocktails.data.remote.RetrofitInstance
import com.example.appcocktails.data.repository.CocktailRepositoryImpl
import com.example.appcocktails.domain.model.Cocktail

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val cocktail: Cocktail) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepositoryImpl(
        api = RetrofitInstance.api,
        dao = CocktailDatabase.getInstance(application).cocktailDao()
    )

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    fun loadCocktail(id: String) {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            try {
                val cocktail = repository.getCocktailById(id)
                if (cocktail != null) _uiState.value = DetailUiState.Success(cocktail)
                else _uiState.value = DetailUiState.Error("Cóctel no encontrado")
            } catch (e: Exception) {
                _uiState.value = DetailUiState.Error(e.message ?: "Error desconocido")
            }
        }
    }
}