package ar.edu.itba.hci.wallx.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.components.*
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit,
    viewModel: WallXViewModel
) {
    viewModel.getUser()
    Column(modifier = modifier.fillMaxSize()) {
        AvailableMoney(viewModel, onNavigate)
        YourInfo(viewModel)
        LastMovements(viewModel)
    }
}

@Composable
fun AvailableMoney(viewModel: WallXViewModel, onNavigate: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    WallXElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            // Balance Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    WallXText(
                        text = stringResource(R.string.dinero_disponible),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    WallXVerticalSpacer(8.dp)
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        WallXText(
                            text = if (uiState.see) (uiState.accountDetail?.balance ?: 0.0).toString() else "*****",
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        WallXHorizontalSpacer(8.dp)
                        WallXIconButton(
                            onClick = { viewModel.toggleSee() },
                            icon = if (uiState.see) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (uiState.see) 
                                stringResource(R.string.ocultar) + stringResource(R.string.saldo)
                            else 
                                stringResource(R.string.mostrar) + stringResource(R.string.saldo)
                        )
                    }
                }
            }

            WallXVerticalSpacer(24.dp)

            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionIconButton(
                    icon = Icons.Filled.Add,
                    label = stringResource(R.string.ingresar)
                ) {
                    onNavigate(AppDestinations.INGRESAR_DINERO.route)
                }
                ActionIconButton(
                    icon = Icons.Filled.CreditCard,
                    label = stringResource(R.string.tarjetas)
                ) {
                    onNavigate(AppDestinations.TARJETAS.route)
                }
                ActionIconButton(
                    icon = ImageVector.vectorResource(R.drawable.send_money_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    label = stringResource(R.string.transferir)
                ) {
                    onNavigate(AppDestinations.TRANSFERENCIAS.route)
                }
            }
        }
    }
}

@Composable
fun ActionIconButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WallXFilledIconButton(
            onClick = onClick,
            modifier = Modifier.size(56.dp),
            icon = icon,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
        WallXVerticalSpacer(8.dp)
        WallXText(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun InfoCard(text: String) {
    WallXCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WallXText(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            WallXIconButton(
                onClick = { /* TODO: Implement copy functionality */ },
                icon = Icons.Filled.ContentCopy,
                contentDescription = "copy",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun LastMovements(viewModel: WallXViewModel) {
    WallXElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(320.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.History,
                    contentDescription = "History",
                    modifier = Modifier.size(28.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                WallXHorizontalSpacer(12.dp)
                WallXTitle(
                    text = stringResource(R.string.ultimos_movimientos),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            WallXVerticalSpacer(20.dp)

            // Movements List
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SingleMovement(
                    account = "Manuel Othaceguey",
                    date = "18/06/2025",
                    amount = "$5.000",
                    isPositive = false
                )
                SingleMovement(
                    account = "Belupetri",
                    date = "18/06/2025",
                    amount = "$40.000",
                    isPositive = true
                )
                SingleMovement(
                    account = "sushiPopPremium",
                    date = "18/06/2025",
                    amount = "$9.000",
                    isPositive = false
                )
            }
        }
    }
}

@Composable
fun SingleMovement(
    account: String,
    date: String,
    amount: String,
    isPositive: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            WallXText(
                text = account,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            WallXText(
                text = date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        WallXText(
            text = amount,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = if (isPositive) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.error
        )
    }
}

