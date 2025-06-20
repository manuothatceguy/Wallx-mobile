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
    modifier: Modifier = Modifier,
    startRoute: String,
    currentRoute : String?,
    viewModel: WallXViewModel,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_DE_SESION.route
    ) {
        composable(
            route = AppDestinations.INICIO_DE_SESION.route

        ) {
            LoginScreen(
                wallXViewModel = viewModel,
                onLoginSuccess = {
                    println("Login success")
                }
            )
        }
        composable(AppDestinations.REGISTRO.route) {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    println("Register success")
                }
            )
        }
        composable(AppDestinations.DASHBOARD.route) {
//            DashboardScreen(modifier)
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
//            VerifyScreen(
//                onVerifySuccess = {
//                    navController.navigate(AppDestinations.INICIO_DE_SESION.route) {
//                        popUpTo(AppDestinations.VERIFICAR.route) { inclusive = true }
//                    }
//                }
//            )
        }
    }
}

fun navigateTo(){

}

