package ar.edu.itba.hci.wallx.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class Routes(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    DASHBOARD("dashboard", "Dashboard", Icons.Filled.Home),
    TARJETAS("tarjetas", "Tarjetas", Icons.Filled.CreditCard),
    SERVICIOS("servicios", "Servicios", Icons.Filled.DesignServices),
    MOVIMIENTOS("movimientos", "Movimientos", Icons.Filled.History),
    TRANSFERENCIAS("transferencias", "Transferencias", Icons.AutoMirrored.Filled.Send)
}