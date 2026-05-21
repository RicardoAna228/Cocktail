package com.example.appcocktails

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.appcocktails.notifications.NotificationHelper
import com.example.appcocktails.ui.navigation.AppNavigation
import com.example.appcocktails.ui.theme.CocktailsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear canal de notificaciones (obligatorio API 26+)
        NotificationHelper.createChannel(this)

        setContent {
            CocktailsTheme () {
                NotificationPermissionHandler()
                AppNavigation()
            }
        }
    }
}

@Composable
fun NotificationPermissionHandler() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return

    var showRationale by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted) showRationale = true
    }

    LaunchedEffect(Unit) {
        if (!permissionRequested) {
            permissionRequested = true
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text("Notificaciones desactivadas") },
            text = {
                Text(
                    "Las notificaciones están deshabilitadas. " +
                            "Actívalas en Ajustes para saber cuándo se cargan nuevos cócteles."
                )
            },
            confirmButton = {
                TextButton(onClick = { showRationale = false }) { Text("Entendido") }
            }
        )
    }
}