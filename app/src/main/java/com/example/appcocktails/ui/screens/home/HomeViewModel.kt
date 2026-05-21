package com.example.appcocktails.ui.screens.home


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.SocketTimeoutException
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.appcocktails.data.local.CocktailDatabase
import com.example.appcocktails.data.remote.RetrofitInstance
import com.example.appcocktails.data.repository.CocktailRepositoryImpl
import com.example.appcocktails.domain.model.Cocktail
import com.example.appcocktails.notifications.NotificationHelper

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val cocktails: List<Cocktail>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = CocktailRepositoryImpl(
        api = RetrofitInstance.api,
        dao = CocktailDatabase.getInstance(application).cocktailDao()
    )

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init { loadCocktails() }

    fun loadCocktails() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            try {
                val cocktails = repository.getCocktails()
                _uiState.value = HomeUiState.Success(cocktails)
                // Dispara notificación al cargar exitosamente
                NotificationHelper.showDataLoadedNotification(getApplication(), cocktails.size)
            } catch (e: Exception) {
                val message = when (e) {
                    is SocketTimeoutException -> "La solicitud tardó demasiado. Revisa tu conexión y vuelve a intentar."
                    is IOException -> "No se pudo conectar a internet. Verifica tu conexión y vuelve a intentar."
                    else -> e.message ?: "Error desconocido"
                }
                _uiState.value = HomeUiState.Error(message)
            }
        }
    }
}