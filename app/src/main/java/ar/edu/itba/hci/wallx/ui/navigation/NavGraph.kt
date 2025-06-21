package ar.edu.itba.hci.wallx.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.screens.auth.LoginScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.RegisterScreen
import ar.edu.itba.hci.wallx.ui.screens.auth.VerifyScreen
import ar.edu.itba.hci.wallx.ui.screens.dashboard.DashboardScreen
import ar.edu.itba.hci.wallx.ui.screens.dashboard.IngresarDineroScreen
import ar.edu.itba.hci.wallx.ui.screens.movimientos.MovimientoDetalleScreen
import ar.edu.itba.hci.wallx.ui.screens.movimientos.MovimientosScreen
import ar.edu.itba.hci.wallx.ui.screens.perfil.PerfilScreen
import ar.edu.itba.hci.wallx.ui.screens.servicios.ServiciosScreen
import ar.edu.itba.hci.wallx.ui.screens.tarjetas.AgregarTarjetaScreen
import ar.edu.itba.hci.wallx.ui.screens.tarjetas.TarjetasScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    startRoute: String,
    currentRoute : String?,
    viewModel: WallXViewModel,
    navController: NavHostController = rememberNavController(),
    navGuard : (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,

    ) {
        composable(
            route = AppDestinations.INICIO_DE_SESION.route

        ) {
            LoginScreen(
                wallXViewModel = viewModel,
                onNavigateTo = navGuard
            )
        }
        composable(AppDestinations.REGISTRO.route) {
            RegisterScreen(
                viewModel = viewModel,
                onRegisterSuccess = {
                    navGuard(AppDestinations.VERIFICAR.route)
                }
            )
        }
        composable(AppDestinations.DASHBOARD.route) {
            DashboardScreen(
                viewModel = viewModel,
                modifier = modifier,
                onNavigate = navGuard
            )
        }
        composable(AppDestinations.MOVIMIENTOS.route) {
            MovimientosScreen(
                modifier,
                wallXViewModel = viewModel,
                onNavigateTo = navGuard
            )
        }
        composable(AppDestinations.TRANSFERENCIAS.route) {
            //TransferenciasScreen(modifier)
        }
        composable(AppDestinations.NUEVA_TRANSFERENCIA.route) {
            // NuevaTransferenciaScreen(modifier)
        }
        composable(AppDestinations.SERVICIOS.route) {
            ServiciosScreen(modifier, onNavigate = navGuard)
        }
        composable(AppDestinations.TARJETAS.route) {
            TarjetasScreen(modifier, wallXViewModel = viewModel ,onNavigateTo = navGuard)
        }
        composable(AppDestinations.VERIFICAR.route) {
            VerifyScreen(
                viewModel = viewModel,
                onVerifySuccess = {
                    navGuard(AppDestinations.DASHBOARD.route)
                }
            )
        }
        composable(AppDestinations.INGRESAR_DINERO.route) {
            IngresarDineroScreen(modifier, wallXViewModel = viewModel, onNavigate = navGuard)
        }
        composable(AppDestinations.AGREGAR_TARJETA.route) {
            AgregarTarjetaScreen(modifier)
        }
        composable (
            route=AppDestinations.MOVIMIENTO_DETALLE.route,
        ){

           MovimientoDetalleScreen(modifier,  wallXViewModel = viewModel,  onNavigateTo = navGuard)
        }
        composable(AppDestinations.PERFIL.route) {
            PerfilScreen(
                modifier,
                viewModel
            )
        }
    }
}
