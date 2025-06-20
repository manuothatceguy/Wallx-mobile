package ar.edu.itba.hci.wallx.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXApplication
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.navigation.AppNavGraph
import ar.edu.itba.hci.wallx.ui.screens.auth.RegisterScreen
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
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

    val configuration = LocalConfiguration.current
    val tablet = configuration.screenWidthDp >= 600

    val authRoutes = listOf(
        AppDestinations.INICIO_DE_SESION.route,
        AppDestinations.REGISTRO.route,
        AppDestinations.VERIFICAR.route
    )

    val includeBottomBar = currentRoute !in authRoutes
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { if (includeBottomBar) SideBar(viewModel, currentRoute, navController) }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { if (currentRoute in authRoutes) AuthTopBar(viewModel) else TopBar(viewModel, scope, drawerState, navController, currentRoute)},
            bottomBar = { if (currentRoute == AppDestinations.DASHBOARD.route) BottomBar(viewModel, scope, drawerState) },

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
        AppDestinations.NUEVA_TRANSFERENCIA.route,
        // agregar tarjeta
        // detalles de movimiento
        // detalles de tarjeta
        // etc.
    )

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            if (currentRoute !in backRoutes) {
                IconButton(onClick = {
                    scope.launch {
                        if (drawerState.isClosed == true) drawerState.open()
                        else drawerState.close()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Menú")
                }
            } else {
                IconButton(onClick = { navController.popBackStack() }) {
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
                    Icon(
                        imageVector = Icons.Filled.AccountCircle,
                        contentDescription = "Perfil"
                    )
                    Text("${stringResource(R.string.saludo)} ${(", " + uiState.completeUserDetail?.firstName)}")
                }
            }
        },
        actions = {
            Button(onClick = { /* TODO: Acción ayuda */ }) {
                Icon(Icons.AutoMirrored.Filled.Help, contentDescription = "Ayuda")
                Text("Ayuda")
            }
        }
    )
}

@Composable
fun AuthTopBar(viewModel: WallXViewModel) {
    Card {
        Text(
            text = "WallX" ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewBottomBar(){
    BottomBar(null, rememberCoroutineScope(), rememberDrawerState(initialValue = DrawerValue.Closed))
}

@Composable
fun BottomBar(viewModel: WallXViewModel?, scope: CoroutineScope, drawerState: DrawerState) {
    BottomAppBar (
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                IconButton(
                    onClick = {
                        scope.launch {  if (drawerState.isClosed) drawerState.open() else drawerState.close() }
                    }
                ) {
                    Icon(Icons.Filled.Menu, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        println("hola!!")
                    }
                ) {
                    Icon(AppDestinations.MOVIMIENTOS.icon, contentDescription = null)
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton( // TODO: if is tablet, mostrar extendido
                onClick = {
                    println("qr!!!")
                }
            ) {
                Icon(Icons.Filled.QrCodeScanner, "Scan QR Code")
            }
        },

    )
}

@Composable
fun SideBar(viewModel: WallXViewModel, currentRoute: String?, navController: NavController, ) {
    val uiState by viewModel.uiState.collectAsState()
    val drawerRoutes = listOf(
        AppDestinations.DASHBOARD,
        AppDestinations.MOVIMIENTOS,
        AppDestinations.TRANSFERENCIAS,
        AppDestinations.SERVICIOS,
        AppDestinations.TARJETAS
    )
    ModalDrawerSheet{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Text(uiState.completeUserDetail?.firstName + " " + uiState.completeUserDetail?.lastName, modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()

            for (destination in drawerRoutes) {
                NavigationDrawerItem(
                    label = { Text(stringResource(destination.title)) },
                    selected = currentRoute == destination.route,
                    onClick = { navGuard(navController, destination.route, uiState.isAuthenticated) }
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text(stringResource(R.string.cerrar_sesion)) },
                selected = false,
                icon = { Icon(Icons.AutoMirrored.Outlined.Logout, contentDescription = null) },
                onClick = {
                    viewModel.logout()
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
