package ar.edu.itba.hci.wallx.ui.screens.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreTime
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.screens.MainApp
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(modifier: Modifier=Modifier)
{

    Column(modifier = modifier.fillMaxSize()) {
        //Dinero disponible
        AvailableMoney()
        YourInfo()
        LastMovements()
    }

}

@Composable
fun AvailableMoney(){
    var see by remember { mutableStateOf(true) }

    val backgroundCardColor = Color(0xFF63B6A6)

    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(250.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundCardColor),
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
                    modifier = Modifier.size(30.dp)
                )
                Text(
                    text = "Dinero disponible",
                    style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start=15.dp)
                )
            }
            HorizontalDivider(
                color = Color(0xFF5c978c),
                thickness = 1.dp,
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color(0xFF5c978c)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            )
            {

                Text(
                    text = if (see) "$265,986" else "*****",
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.padding(end = 4.dp),
                    color = Color.White

                )

                IconButton(onClick = { see = !see }) {
                    Icon(
                        imageVector = if (see) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (see) "Ocultar saldo" else "Mostrar saldo",
                        modifier = Modifier.size(30.dp)

                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ActionIconButton(icon = Icons.Filled.Add, label = "Ingresar") {
                    // Acción de ingresar dinero
                }
                ActionIconButton(
                    icon = ImageVector.vectorResource(R.drawable.send_money_24dp_e3e3e3_fill0_wght400_grad0_opsz24),
                    label = "Transferir"
                ) {
                    // acción
                }
                ActionIconButton(icon = Icons.Filled.CreditCard, label = "Tarjetas") {
                    // Mostrar info o navegar
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
                containerColor = Color(0xFFEFF6F6),
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(0.dp)

        )
        {
            Icon(
                icon,
                contentDescription = label,
                modifier = Modifier.size(29.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, fontSize = MaterialTheme.typography.bodyLarge.fontSize, fontWeight = FontWeight.SemiBold, color = Color.White)
    }
}

@Composable
fun MoneyVisibility() {
    var see by remember { mutableStateOf(true) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = if (see) "$1000" else "****",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold)        )

        Button(onClick = { see = !see }) {
            Icon(
                imageVector = if (see) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                contentDescription = if (see) "Ocultar saldo" else "Mostrar saldo"
            )
        }
    }
}


@Composable
fun YourInfo(){
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(210.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF92cac0)) // o Secondary, si querés un bloque destacado
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
                    text = "Tu informacion",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold, color=Color.DarkGray)
                )
            }
            HorizontalDivider(thickness = 1.dp,color = Color(0xFF5c978c))
            InfoCard("Alias: juan.wallx.arg")
            InfoCard("CVU: 012345678912345679")

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

        colors = CardDefaults.cardColors(containerColor = Color(0Xff6da89d))
    ){
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column () {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.SemiBold, color = Color.White
                    )
                )
            }
            Column( horizontalAlignment= Alignment.End)
            {
                Icon(
                    Icons.Filled.ContentCopy,
                    contentDescription = "copy",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

    }
}

@Composable
fun LastMovements(){
    Card(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(280.dp),
        colors = CardDefaults.cardColors(containerColor = Secondary) // o Secondary, si querés un bloque destacado
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
                    modifier = Modifier.size(30.dp))

                Text(
                    text = "Ultimos movimentos",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.SemiBold
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
fun Single_movement(account: String, date: String, amount: String, isPositive: Boolean){
    Card (
        modifier = Modifier
        .fillMaxWidth()
        .height(70.dp)
        .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0Xffd6fff4))
    )
    {
        Row( modifier = Modifier
            .fillMaxSize() ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column (modifier = Modifier.padding(horizontal = 8.dp))
            {
                Row {
                    Text(
                        text =(if (isPositive) "+ " else "- " )+amount,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                        fontWeight = FontWeight.Black,
                        color = if(isPositive) Color(0Xff139d17) else Color(0Xffad2626)
                    )
                }

            }

            Column  (modifier = Modifier.padding(horizontal = 8.dp), horizontalAlignment= Alignment.End)
            {
                Text(
                    text = account,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(text=date,
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray)

            }
        }

    }

}


@Preview(showSystemUi = true)
@Composable
fun DashboardPreview() {
    WallxTheme {
        DashboardScreen()
    }
}


