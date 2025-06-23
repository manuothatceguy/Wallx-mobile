package ar.edu.itba.hci.wallx.ui.screens.dashboard

import android.content.ClipData
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import android.content.ClipboardManager
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.runtime.*
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.model.Payment
import ar.edu.itba.hci.wallx.ui.components.YourInfo
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.screens.movimientos.isPositive
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.InfoCardColor
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Success
import ar.edu.itba.hci.wallx.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DashboardScreen(modifier: Modifier, onNavigate: (String) -> Unit, viewModel : WallXViewModel) {
    viewModel.getUser()
    LazyColumn(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        item {
            AvailableMoney(viewModel, onNavigate)
        }
        item {
            YourInfo(viewModel)
        }
        item {
            LastMovements(viewModel,onNavigate)
        }
    }

}

@Composable
fun AvailableMoney(viewModel: WallXViewModel, onNavigate: (String) -> Unit){

    val uiState by viewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.primaryContainer) ,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Filled.Wallet,
                    contentDescription = "Wallet",
                    modifier = Modifier.size(30.dp),
                    tint=  MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource( R.string.dinero_disponible),
                    style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start=15.dp),
                    color =  MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            HorizontalDivider(
                color = Info,
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            )
            {

                Text(
                    text = if (uiState.see) "$ %.2f".format(uiState.accountDetail?.balance ?: 0.0) else "*****",
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(end = 4.dp),
                    color = White

                )

                IconButton(onClick = { viewModel.toggleSee() }) {
                    Icon(
                        imageVector = if (uiState.see) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (uiState.see) (stringResource(R.string.ocultar)+ stringResource(R.string.saldo)) else(stringResource(R.string.mostrar)+ stringResource(R.string.saldo)),
                        modifier = Modifier.size(30.dp)

                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            )
            {
                ActionIconButton(icon = Icons.Filled.Add, label = stringResource( R.string.ingresar  ))
                {
                    onNavigate(AppDestinations.INGRESAR_DINERO.route)
                }
                ActionIconButton(icon = Icons.Filled.CreditCard, label = stringResource( R.string.tarjetas  ))
                {
                    onNavigate(AppDestinations.TARJETAS.route)
                }
                ActionIconButton(
                    icon = ImageVector.vectorResource(R.drawable.send_money_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    label = stringResource( R.string.transferir  ),
                )
                {
                    onNavigate(AppDestinations.TRANSFERENCIAS.route)
                }

            }

        }

    }
}

@Composable
fun ActionIconButton(icon: ImageVector, label: String,    onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            onClick = onClick,
            modifier = Modifier.size(60.dp).padding(0.dp),
            shape = CircleShape,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceBright,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Icon(
                icon,
                contentDescription = label,
                modifier = Modifier.size(29.dp),
                tint=MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
fun InfoCard(text: String){
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp)
            .padding(vertical = 1.dp),

        colors = CardDefaults.cardColors(containerColor = InfoCardColor)
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium, color = White
                    )
                )
            }
            Column( horizontalAlignment= Alignment.End)
            {
                val context = LocalContext.current
                val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("text", text)
                var copy by remember { mutableStateOf(false) }
                val coroutineScope = rememberCoroutineScope()
                Button(
                    onClick = {
                        clipboardManager.setPrimaryClip(clip)
                        copy = true
                        coroutineScope.launch {
                            delay(3000)
                            copy = false
                        }
                    }
                ) {
                    Icon(
                        if(copy) Icons.Filled.Check else Icons.Filled.ContentCopy,
                        contentDescription = "copy",
                        modifier = Modifier.size(25.dp),
                        tint= MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }

    }
}

@Composable
fun LastMovements(
    viewModel: WallXViewModel,
    onNavigateTo: (String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor =  MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
            ) {
                Icon(
                    Icons.Filled.History,
                    contentDescription = "History",
                    modifier = Modifier.size(30.dp),
                    tint=MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(Modifier.size(9.dp))
                Text(
                    text =stringResource( R.string.ultimos_movimientos ),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color=MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.clickable {
                        onNavigateTo(AppDestinations.MOVIMIENTOS.route)
                    }
                )
            }
            val uiState by viewModel.uiState.collectAsState()
            val payments = uiState.paymentsDetail ?: emptyList()
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                payments.take(3).forEach { movement ->
                    MovementOverview(movement, viewModel)
                }
            }

        }
    }
}

@Composable
fun MovementOverview(
    movement: Payment,
    wallXViewModel: WallXViewModel
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp)) // verde suave
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .heightIn(min= 50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val isPositive = isPositive(wallXViewModel,movement)
        Text(
            text =  (if(isPositive) "+ " else "- ")+"$ " + movement.amount.toString(),
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            color = if(isPositive) Success else MaterialTheme.colorScheme.error
        )
        Text(
            text = if(isPositive)(movement.payer?.firstName ?: "") + " " + (movement.payer?.lastName ?: "") else (movement.receiver?.firstName ?: "") + " " + (movement.receiver?.lastName ?: ""),
            modifier = Modifier.weight(1f),
            fontSize = 14.sp
        )
    }
}
