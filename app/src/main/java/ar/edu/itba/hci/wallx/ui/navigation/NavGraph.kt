package ar.edu.itba.hci.wallx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.ui.screens.auth.LoginScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.RegisterScreen
import ar.edu.itba.hci.wallx.ui.screens.dashboard.DashboardScreen
import ar.edu.itba.hci.wallx.ui.viewmodel.UserViewModel

@Composable
fun AppNavGraph(
    userViewModel: UserViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    val user by userViewModel.user.collectAsState()
    val isLoggedIn = user != null
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) AppDestinations.DASHBOARD.route else AppDestinations.INICIO_DE_SESION.route
    ) {
        composable(AppDestinations.INICIO_DE_SESION.route) {
            LoginScreen(modifier)
        }
        composable(AppDestinations.REGISTRO.route) {
            RegisterScreen(modifier)
        }
        composable(AppDestinations.DASHBOARD.route) {
            DashboardScreen(modifier)
        }
        composable(AppDestinations.MOVIMIENTOS.route) {
            // MovimientosScreen(modifier)
        }
        composable(AppDestinations.TRANSFERENCIAS.route) {
            // TransferenciasScreen(modifier)
        }
        composable(AppDestinations.NUEVA_TRANSFERENCIA.route) {
            // NuevaTransferenciaScreen(modifier)
        }
        composable(AppDestinations.SERVICIOS.route) {
            // ServiciosScreen(modifier)
        }
        composable(AppDestinations.TARJETAS.route) {
            // TarjetasScreen(modifier)
        }
        composable(AppDestinations.VERIFICAR.route) {
            // VerificarCuentaScreen(modifier)
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            navController.navigate(AppDestinations.INICIO_DE_SESION.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }
}