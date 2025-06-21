package ar.edu.itba.hci.wallx.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.Error
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.InfoCardColor
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Light
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.Success
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.SurfaceVariant
import ar.edu.itba.hci.wallx.ui.theme.White

@Composable
fun DashboardScreen(modifier: Modifier, onNavigate: (String) -> Unit, viewModel : WallXViewModel) {
    viewModel.getUser()
    Column(modifier = modifier.fillMaxSize()) {
        //Dinero disponible
        AvailableMoney(viewModel, onNavigate)
        YourInfo(viewModel)
        LastMovements(viewModel)
    }

}

@Composable
fun AvailableMoney(viewModel: WallXViewModel, onNavigate: (String) -> Unit){

    val uiState by viewModel.uiState.collectAsState()

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = Interactive),
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
                    text = if (uiState.see) (uiState.accountDetail?.balance ?: 0.0).toString() else "*****",
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
                ActionIconButton(
                    icon = ImageVector.vectorResource(R.drawable.send_money_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    label = stringResource( R.string.transferir  ),
                )
                {
                    onNavigate(AppDestinations.TRANSFERENCIAS.route)
                }
                ActionIconButton(icon = Icons.Filled.CreditCard, label = stringResource( R.string.tarjetas  ))
                {
                    onNavigate(AppDestinations.TARJETAS.route)
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
fun YourInfo(viewModel: WallXViewModel){
    val uiState by viewModel.uiState.collectAsState()
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .heightIn(min = 210.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) // o Secondary, si querés un bloque destacado
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
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = stringResource(R.string.tu_información),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold, color=MaterialTheme.colorScheme.onSecondaryContainer)
                )
            }
            HorizontalDivider(thickness = 1.dp,color = SurfaceVariant)
            var aliasString = if(uiState.accountDetail != null) uiState.accountDetail!!.alias else "Error"
            var cvuString = if(uiState.accountDetail != null) uiState.accountDetail!!.cvu else "Error"
            Text(stringResource(R.string.alias))
            InfoCard( aliasString)
            Text(stringResource(R.string.alias))
            InfoCard(cvuString)
            HorizontalDivider(thickness = 1.dp,color = MaterialTheme.colorScheme.outline)

        }
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
                Icon(
                    Icons.Filled.ContentCopy,
                    contentDescription = "copy",
                    modifier = Modifier.size(25.dp),
                    tint= MaterialTheme.colorScheme.surfaceVariant

                )
            }
        }

    }
}

@Composable
fun LastMovements(viewModel: WallXViewModel){
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer) // o Secondary, si querés un bloque destacado
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

                Text(
                    text =stringResource( R.string.ultimos_movimientos ),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color=MaterialTheme.colorScheme.onSecondaryContainer
                    )
                )
            }
            Column {
                Single_movement(
                    account = "Manuel Othaceguey",
                    date = "18/06/2025",
                    amount = "$5.000",
                    isPositive = false
                )
                Single_movement(
                    account = "Belupetri",
                    date = "18/06/2025",
                    amount = "$40.000",
                    isPositive = true
                )
                Single_movement(
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
fun Single_movement(account: String, date: String, amount: String, isPositive: Boolean) {
    Card(
        modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceBright)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(horizontal = 8.dp))
            {
                Row {
                    Text(
                        text = (if (isPositive) "+ " else "- ") + amount,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Black,
                        color = if(isPositive) Success else Error
                    )
                }

            }

            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.End
            )
            {
                Text(
                    text = account,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = date,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

            }
        }

    }

}

