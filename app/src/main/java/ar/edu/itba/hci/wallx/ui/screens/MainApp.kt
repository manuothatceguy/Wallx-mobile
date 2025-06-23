package ar.edu.itba.hci.wallx.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXApplication
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.components.DeviceLayout
import ar.edu.itba.hci.wallx.ui.components.errorManager
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.navigation.AppNavGraph
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainApp (
    viewModel : WallXViewModel = viewModel(factory = WallXViewModel.provideFactory(LocalContext.current.applicationContext as WallXApplication)))
{
    val uiState by viewModel.uiState.collectAsState()
    val navController = rememberNavController()
    val startRoute = if (uiState.isAuthenticated) AppDestinations.DASHBOARD.route else AppDestinations.INICIO_DE_SESION.route
    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val authRoutes = AppDestinations.entries.filter{entry -> entry.requiresAuth}.map { entry -> entry.route }
    val currentRouteIsAuth = currentRoute in authRoutes
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    viewModel.getSee()

    BoxWithConstraints {
        val tablet = this.maxWidth > 600.dp
        if(tablet){
            Row {
                if(uiState.isAuthenticated){
                    NavigationRail(
                        containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.8f)
                    ) {

                        val drawerRoutes = listOf(
                            AppDestinations.DASHBOARD,
                            AppDestinations.MOVIMIENTOS,
                            AppDestinations.SERVICIOS,
                            AppDestinations.TARJETAS,
                            AppDestinations.PERFIL
                        )

                        drawerRoutes.forEach { destination ->
                            NavigationRailItem(
                                label = {
                                    Text(
                                        text = stringResource(destination.title),
                                    )
                                },
                                selected = currentRoute == destination.route,
                                onClick = { navGuard(navController, destination.route, true) },
                                enabled = true,
                                alwaysShowLabel = true,
                                icon = {
                                    Icon(destination.icon, contentDescription = null)
                                }
                            )
                        }

                        NavigationRailItem(
                            label = {
                                Text(
                                    text = stringResource(R.string.cerrar_sesion),
                                )
                            },
                            selected = false,
                            onClick = {
                                scope.launch {
                                    viewModel.logout()
                                }
                            },
                            enabled = true,
                            alwaysShowLabel = true,
                            icon = {
                                Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = stringResource(R.string.cerrar_sesion))
                            }
                        )
                    }
                }
                MainScaffold(
                    snackbarHostState,
                    currentRouteIsAuth,
                    startRoute,
                    currentRoute,
                    navController,
                    viewModel,
                    scope,
                    drawerState
                )
            }



        } else {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    if (currentRouteIsAuth && uiState.isAuthenticated) {
                        SideBar(viewModel, currentRoute, navController, drawerState)
                    }
                },
                content = {
                    MainScaffold(
                        snackbarHostState,
                        currentRouteIsAuth,
                        startRoute,
                        currentRoute,
                        navController,
                        viewModel,
                        scope,
                        drawerState
                    )
                }
            )
        }
    }




    val errorMessageRes = uiState.error?.let { error ->
        stringResource(errorManager(error.message))
    }

    LaunchedEffect(uiState.completeUserDetail) {
        if (uiState.isAuthenticated && uiState.completeUserDetail == null) {
            snackbarHostState.showSnackbar(errorMessageRes?: "Error")
            viewModel.clearError()
        }
    }
}


@Composable
fun MainScaffold(
    snackbarHostState : SnackbarHostState,
    currentRouteIsAuth : Boolean,
    startRoute : String,
    currentRoute : String?,
    navController : NavHostController,
    viewModel : WallXViewModel,
    scope : CoroutineScope,
    drawerState : DrawerState
) {
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { if (currentRouteIsAuth && uiState.isAuthenticated && uiState.completeUserDetail != null) TopBar(viewModel, scope, drawerState, navController, currentRoute) },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.error
                )
            }
        }
    ) { padding ->
        Surface{
            AppNavGraph(
                startRoute = startRoute,
                currentRoute = currentRoute,
                viewModel = viewModel,
                navController = navController,
                modifier = Modifier.padding(padding),
            ) { route ->
                navGuard(navController, route, uiState.isAuthenticated)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    viewModel: WallXViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController,
    currentRoute: String?
) {
    val uiState by viewModel.uiState.collectAsState()
    val backRoutes = listOf(
        AppDestinations.MOVIMIENTO_DETALLE.route,
        AppDestinations.INGRESAR_DINERO.route,
        AppDestinations.AGREGAR_TARJETA.route,
        AppDestinations.PAGAR_SERVICIO.route
    )
    val firstName = uiState.completeUserDetail?.firstName ?: ""

    val tablet = LocalConfiguration.current.screenWidthDp > 300

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(0.8f),
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            if (currentRoute !in backRoutes && !tablet) {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed) drawerState.open()
                        else drawerState.close()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menú")
                }
            }
            if(currentRoute in backRoutes) {
                IconButton(onClick = {
                    viewModel.setCurrentPayment(null)
                    navController.popBackStack()
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }

        },
        title = {
            if (currentRoute == AppDestinations.DASHBOARD.route) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("${stringResource(R.string.saludo)}${(", $firstName")}",
                        modifier = Modifier.clickable { navController.navigate(AppDestinations.PERFIL.route) },
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        },
        actions = {
            Button(onClick = { /* TODO: Acción ayuda */ }) {
                Icon(Icons.AutoMirrored.Filled.Help, contentDescription = stringResource(R.string.ayuda))
                Text(stringResource(R.string.ayuda))
            }
        }
    )
}

@Composable
fun SideBar(
    viewModel: WallXViewModel,
    currentRoute: String?,
    navController: NavController,
    drawerState: DrawerState,
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerRoutes = listOf(
        AppDestinations.DASHBOARD,
        AppDestinations.MOVIMIENTOS,
        AppDestinations.SERVICIOS,
        AppDestinations.TARJETAS
    )
    val firstName = uiState.completeUserDetail?.firstName ?: ""
    val lastName = uiState.completeUserDetail?.lastName ?: ""

    ModalDrawerSheet{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Text("$firstName $lastName", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()

            for (destination in drawerRoutes) {
                NavigationDrawerItem(
                    label = { Text(
                        text = stringResource(destination.title),
                        color = MaterialTheme.colorScheme.onSurface
                    ) },
                    selected = currentRoute == destination.route,
                    icon = { Icon(destination.icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                    onClick = { navGuard(navController, destination.route, uiState.isAuthenticated) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            NavigationDrawerItem(
                label = { Text(
                    text = stringResource(R.string.perfil),
                    color = MaterialTheme.colorScheme.onSurface
                ) },
                selected = false,
                icon = { Icon(AppDestinations.PERFIL.icon, contentDescription = null,tint = MaterialTheme.colorScheme.onSurface) },
                onClick = {
                    scope.launch {
                        drawerState.close()
                        navGuard(navController, AppDestinations.PERFIL.route, uiState.isAuthenticated)
                    }
                },
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            NavigationDrawerItem(
                label = { Text(
                    text = stringResource(R.string.cerrar_sesion),
                    color = MaterialTheme.colorScheme.onSurface
                ) },
                selected = false,
                icon = { Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null, tint = MaterialTheme.colorScheme.onSurface) },
                onClick = {
                    scope.launch {
                        drawerState.close()
                        viewModel.logout()
                    }
                },
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

fun navGuard(navController : NavController, route : String, isAuth : Boolean) {
    val authRoutes = listOf(
        AppDestinations.INICIO_DE_SESION.route,
        AppDestinations.REGISTRO.route,
        AppDestinations.VERIFICAR.route
    )

    if(isAuth || route in authRoutes){
        if(navController.currentDestination?.route != route) {
            navController.navigate(route) {
                launchSingleTop = true
                restoreState = true
                if(route in listOf(AppDestinations.INICIO_DE_SESION.route, AppDestinations.DASHBOARD.route)){
                    popUpTo(AppDestinations.INICIO_DE_SESION.route) { inclusive = true }
                }
            }
        } else {
            navController.navigate(AppDestinations.INICIO_DE_SESION.route) {
                popUpTo(AppDestinations.INICIO_DE_SESION.route) { inclusive = true }
            }
        }
    }

}
