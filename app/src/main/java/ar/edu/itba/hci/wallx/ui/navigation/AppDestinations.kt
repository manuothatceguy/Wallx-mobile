package ar.edu.itba.hci.wallx.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.ui.graphics.vector.ImageVector
import ar.edu.itba.hci.wallx.R

enum class AppDestinations(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String
) {
    INICIO_DE_SESION(R.string.inicio_de_sesión, Icons.AutoMirrored.Filled.Login, "login"),
    REGISTRO(R.string.registrate, Icons.AutoMirrored.Filled.Login, "registro"),
    DASHBOARD(R.string.dashboard, Icons.Filled.Home, "dashboard"),
    MOVIMIENTOS(R.string.movimientos, Icons.Filled.Payments, "movimientos"),
    TRANSFERENCIAS(R.string.transferencias, Icons.Filled.Payments, "transferencias"),
    NUEVA_TRANSFERENCIA(R.string.nueva_transferencia, Icons.Filled.Payments, "nueva_transferencia"),
    SERVICIOS(R.string.servicios, Icons.Filled.Payments, "servicios"),
    TARJETAS(R.string.tarjetas, Icons.Filled.Payments, "tarjetas"),

}