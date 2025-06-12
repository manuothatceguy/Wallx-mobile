package ar.edu.itba.hci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.ui.screens.dashboard.DashboardScreen
import ar.edu.itba.hci.ui.theme.WallxTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallxTheme {
                MainAppWithBottomBar()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MainAppWithBottomBar() {
    var selectedItemIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            CradledBottomNavBar(
                selectedItemIndex = selectedItemIndex,
                onItemSelected = { index -> selectedItemIndex = index },
                onQrClick = { /* TODO: Acción del QR */ }
            )
        }
    ) { innerPadding ->
        DashboardScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun CradledBottomNavBar(
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    onQrClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        // --- ORDEN CORREGIDO ---

        // 1. DIBUJAMOS LA BARRA INFERIOR PRIMERO (quedará al fondo)
        BottomAppBar(
            modifier = Modifier
                .height(60.dp)
                .align(Alignment.BottomCenter), // Nos aseguramos que se alinee abajo del Box
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ítem de la izquierda
                BottomBarItem(
                    text = "Menú",
                    icon = Icons.Default.Menu,
                    isSelected = selectedItemIndex == 0,
                    onClick = { onItemSelected(0) }
                )

                // El espacio vacío para el botón
                Spacer(modifier = Modifier.weight(1f))

                // Ítem de la derecha
                BottomBarItem(
                    text = "Gastos",
                    icon = Icons.Default.History,
                    isSelected = selectedItemIndex == 1,
                    onClick = { onItemSelected(1) }
                )
            }
        }

        // 2. DIBUJAMOS EL BOTÓN GRANDE AL FINAL (quedará encima de todo)
        Button(
            onClick = onQrClick,
            modifier = Modifier
                .size(72.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.QrCodeScanner,
                contentDescription = "Escanear QR",
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}


@Composable
fun RowScope.BottomBarItem(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    // NOTA: Añadido RowScope. para poder usar Modifier.weight()
    val contentColor =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .weight(1f) // Cada ítem ocupa el mismo espacio
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = contentColor
        )
        Text(
            text = text,
            color = contentColor,
            fontWeight = fontWeight,
            fontSize = 12.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    WallxTheme {
        MainAppWithBottomBar()
    }
}