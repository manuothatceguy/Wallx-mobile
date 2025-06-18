package ar.edu.itba.hci.wallx.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.WallXViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun MainApp (
    viewModel : WallXViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val startRoute = if (uiState.isAuthenticated) "home" else "auth"
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    val configuration = LocalConfiguration.current
    val tablet = configuration.screenWidthDp >= 600


    // revisar resoluciones
    // revisar idioma
}

private fun