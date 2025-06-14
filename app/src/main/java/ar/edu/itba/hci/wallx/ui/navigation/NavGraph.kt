package ar.edu.itba.hci.wallx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ar.edu.itba.hci.wallx.ui.screens.auth.LoginScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.RegisterScreen


@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_DE_SESION.route
    ) {
        composable(AppDestinations.INICIO_DE_SESION.route) {
            LoginScreen(modifier)
        }
        composable(AppDestinations.REGISTRO.route) {
            RegisterScreen(modifier)
        }
    }
}