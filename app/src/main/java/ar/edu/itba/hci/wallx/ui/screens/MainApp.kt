@file:OptIn(ExperimentalMaterial3Api::class)

package ar.edu.itba.hci.wallx.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.WallXViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ar.edu.itba.hci.wallx.WallXApplication
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.navigation.AppNavGraph
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MainApp (
    viewModel : WallXViewModel = viewModel(factory = WallXViewModel.provideFactory(WallXApplication())))
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

    val includeTopBar = currentRoute in authRoutes
    val includeBottomBar = currentRoute !in authRoutes
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { if (includeBottomBar) SideBar() }
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { if (includeTopBar) TopBar(viewModel) },
            bottomBar = { if (includeBottomBar) BottomBar(viewModel, scope, drawerState) },

            ) { padding ->
            Surface{
                AppNavGraph(
                    startRoute = startRoute,
                    currentRoute = currentRoute,
                    viewModel = viewModel,
                    navController = navController,
                    modifier = Modifier.padding(padding)
                )
            }
        }
    }
}

@Composable
fun TopBar(viewModel: WallXViewModel) {
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

@Composable
fun BottomBar(viewModel: WallXViewModel, scope: CoroutineScope, drawerState: DrawerState) {
    BottomAppBar (
        actions = {
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
                Icon(Icons.Filled.Preview, contentDescription = null)
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
        }
    )
}

@Composable
fun SideBar() {
    ModalDrawerSheet{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(12.dp))
            Text("Drawer Title", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()

            Text("Section 1", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Item 1") },
                selected = false,
                onClick = { /* Handle click */ }
            )
            NavigationDrawerItem(
                label = { Text("Item 2") },
                selected = false,
                onClick = { /* Handle click */ }
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            Text("Section 2", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)
            NavigationDrawerItem(
                label = { Text("Settings") },
                selected = false,
                icon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                badge = { Text("20") }, // Placeholder
                onClick = { /* Handle click */ }
            )
            NavigationDrawerItem(
                label = { Text("Help and feedback") },
                selected = false,
                icon = { Icon(Icons.AutoMirrored.Outlined.Help, contentDescription = null) },
                onClick = { /* Handle click */ },
            )
            Spacer(Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
fun MainAppPreview() {
    WallxTheme { MainApp() }
}