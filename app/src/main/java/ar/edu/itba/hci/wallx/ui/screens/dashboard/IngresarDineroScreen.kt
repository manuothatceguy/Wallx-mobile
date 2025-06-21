package ar.edu.itba.hci.wallx.ui.screens.dashboard

import androidx.compose.foundation.Image
import ar.edu.itba.hci.wallx.data.model.Card
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.ui.screens.servicios.ServiciosScreen
import ar.edu.itba.hci.wallx.ui.screens.servicios.textFieldColors
import ar.edu.itba.hci.wallx.ui.screens.tarjetas.AgregarTarjetaScreen
import ar.edu.itba.hci.wallx.ui.theme.Background
import ar.edu.itba.hci.wallx.ui.theme.Error
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.SurfaceVariant
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme
import ar.edu.itba.hci.wallx.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import ar.edu.itba.hci.wallx.ui.components.getBrandLogo
import ar.edu.itba.hci.wallx.ui.components.getCardColor
import ar.edu.itba.hci.wallx.ui.components.CardItem
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import ar.edu.itba.hci.wallx.ui.theme.Black
import ar.edu.itba.hci.wallx.ui.theme.Grey
import ar.edu.itba.hci.wallx.ui.theme.Selected


@Composable
fun IngresarDineroScreen(modifier: Modifier = Modifier) {
    var monto by remember { mutableStateOf("") }
    var selectedCardIndex by remember { mutableStateOf(0) }

    val formatter = SimpleDateFormat("MM/yy", Locale.getDefault())
    val dummyCards = listOf(
        Card(id = 1, type = detectCardBrandFromNumber("4123"), number = "4123", expirationDate = formatter.parse("12/26") ?: Date(), fullName = "Juan Pérez"),
        Card(id = 2, type = detectCardBrandFromNumber("5234"), number = "5234", expirationDate = formatter.parse("03/27") ?: Date(), fullName = "Ana López"),
        Card(id = 3, type = detectCardBrandFromNumber("3765"), number = "3765", expirationDate = formatter.parse("08/25") ?: Date(), fullName = "Carlos Ruiz")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .size(760.dp),
            colors = CardDefaults.cardColors(containerColor = Secondary),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.ingresar_dinero),
                    style = Typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = Black,
                    modifier = Modifier.padding( 16.dp)
                )

                Text(
                    text = stringResource(R.string.seleccionar_tarjeta) + ':',
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryDarken1,
                    modifier = Modifier.align(Alignment.Start).padding(vertical = 19.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(dummyCards) { index, card ->
                        MiniCardItem(
                            card = card,
                            isSelected = index == selectedCardIndex,
                            onClick = { selectedCardIndex = index }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(R.string.monto_deseado) + ':',
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = SecondaryDarken1,
                    modifier = Modifier.align(Alignment.Start).padding(vertical = 19.dp)
                )


                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = monto,
                    onValueChange = { monto = it },
                    placeholder = {
                        Text(
                            text=stringResource(R.string.Ejemplo)+":"+"5000",
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Grey) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    colors = textFieldColors()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val tarjetaSeleccionada = dummyCards[selectedCardIndex]
                        // Aquí podrías usar monto y tarjetaSeleccionada
                    },
                    modifier = Modifier.fillMaxWidth().height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Selected,
                        contentColor = White
                    ),
                ) {
                    Text(text=stringResource(R.string.ingresar), style = MaterialTheme.typography.titleLarge.copy(
                        color = White,
                        fontWeight = FontWeight.Bold)
                    )
                }
            }
        }
    }
}



@Composable
fun MiniCardItem( card: Card,
                  isSelected: Boolean,
                  onClick: () -> Unit
){
    val cardColor = getCardColor(card.type)
    val brandLogo = getBrandLogo(card.type)

    Card(
        modifier = Modifier
            .width(240.dp)
            .height(160.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) cardColor else cardColor.copy(alpha = 0.6f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.align(Alignment.BottomStart)
            ) {
                Text(
                    text = "**** ${card.number.takeLast(4)}",
                    style = Typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White,
                )
                Text(
                    text = card.fullName,
                    style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                )
            }

            Image(
                painter = painterResource(id = brandLogo),
                contentDescription = "Brand logo",
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun IngresarScreenPreview() {
    WallxTheme(dynamicColor = false) {
        IngresarDineroScreen (modifier = Modifier)
    }
}