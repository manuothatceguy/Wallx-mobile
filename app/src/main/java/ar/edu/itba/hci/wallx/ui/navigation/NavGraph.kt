package ar.edu.itba.hci.wallx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.screens.auth.LoginScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.RegisterScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.VerifyScreen
import ar.edu.itba.hci.wallx.ui.screens.dashboard.DashboardScreen

@Composable
fun AppNavGraph(
    viewModel: WallXViewModel,
    navController: NavHostController = rememberNavController(),
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_DE_SESION.route
    ) {
        composable(
            route = AppDestinations.INICIO_DE_SESION.route

        ) {
            LoginScreen(
                modifier,
                wallXViewModel = viewModel,
                onLoginSuccess = {
                    println("Login success")
                }
            )
        }
        composable(AppDestinations.REGISTRO.route) {
            var showSuccess by remember { mutableStateOf(false) }
            val navControllerLocal = navController
            RegisterScreen(
                modifier,
                userViewModel,
                onRegisterSuccess = {
                    showSuccess = true
                }
            )
            if (showSuccess) {
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    kotlinx.coroutines.delay(1200)
                    showSuccess = false
                    navControllerLocal.navigate(AppDestinations.VERIFICAR.route) {
                        popUpTo(AppDestinations.REGISTRO.route) { inclusive = true }
                    }
                }
                androidx.compose.material3.Snackbar(
                    action = {},
                    containerColor = ar.edu.itba.hci.wallx.ui.theme.Success,
                    contentColor = ar.edu.itba.hci.wallx.ui.theme.White
                ) {
                    androidx.compose.material3.Text("¡Registro exitoso! Ahora verificá tu email.")
                }
            }
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
            VerifyScreen(
                modifier,
                userViewModel,
                onVerifySuccess = {
                    navController.navigate(AppDestinations.INICIO_DE_SESION.route) {
                        popUpTo(AppDestinations.VERIFICAR.route) { inclusive = true }
                    }
                }
            )
        }
    }

    LaunchedEffect(isLoggedIn, navController.currentBackStackEntryAsState().value) {
        if (!isLoggedIn) {
            val currentRoute = navController.currentBackStackEntry?.destination?.route
            if (currentRoute != AppDestinations.INICIO_DE_SESION.route && currentRoute != AppDestinations.REGISTRO.route) {
                val destination =
                    if (currentRoute != null && currentRoute != AppDestinations.DASHBOARD.route)
                        AppDestinations.INICIO_DE_SESION.route + "?redirectTo=" + currentRoute
                    else
                        AppDestinations.INICIO_DE_SESION.route
                navController.navigate(destination) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    }
}