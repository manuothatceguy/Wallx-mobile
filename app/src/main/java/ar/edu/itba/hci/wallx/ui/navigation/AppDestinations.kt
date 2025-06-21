package ar.edu.itba.hci.wallx.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCard
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import ar.edu.itba.hci.wallx.R

enum class AppDestinations(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
    val requiresAuth : Boolean
) {
    INICIO_DE_SESION(R.string.inicio_de_sesión, Icons.AutoMirrored.Filled.Login, "login", false),
    REGISTRO(R.string.registrate, Icons.AutoMirrored.Filled.Login, "registro", false),
    DASHBOARD(R.string.dashboard, Icons.Filled.Home, "dashboard", true),
    MOVIMIENTOS(R.string.movimientos, Icons.Filled.Payments, "movimientos", true),
    TRANSFERENCIAS(R.string.transferencias, Icons.Filled.Payments, "transferencias", true),
    NUEVA_TRANSFERENCIA(R.string.nueva_transferencia, Icons.Filled.Payments, "nueva_transferencia", true),
    SERVICIOS(R.string.servicios, Icons.Filled.Payments, "servicios", true),
    TARJETAS(R.string.tarjetas, Icons.Filled.CreditCard, "tarjetas", true),
    VERIFICAR(R.string.verificar_cuenta, Icons.Filled.CheckBox, "verificar", false),
    INGRESAR_DINERO(R.string.ingresar, Icons.Filled.Payments, "ingresar_dinero", true),
    AGREGAR_TARJETA(R.string.agregarTarjeta, Icons.Filled.AddCard, "agregar_tarjeta", true),
    MOVIMIENTO_DETALLE(R.string.detalle, Icons.Filled.Add, "detalle_movimiento", true),
    PERFIL(R.string.perfil, Icons.Filled.Person, "perfil", true)
}