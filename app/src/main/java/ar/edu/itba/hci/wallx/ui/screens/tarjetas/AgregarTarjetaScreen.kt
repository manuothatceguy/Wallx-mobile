package ar.edu.itba.hci.wallx.ui.screens.tarjetas

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.data.network.model.card.NewCardData
import ar.edu.itba.hci.wallx.ui.theme.*
import ar.edu.itba.hci.wallx.ui.components.detectCardBrandFromNumber
import ar.edu.itba.hci.wallx.ui.components.getBrandLogo
import ar.edu.itba.hci.wallx.ui.components.getCardColor
import java.util.Locale

enum class CardType { CREDITO, DEBITO }

@Composable
fun CreditCardPreview(
    modifier: Modifier = Modifier,
    number: String,
    fullName: String,
    expirationDate: String,
    brand: String,
    cvv: String,
    mostrarDorso: Boolean,
) {
    val brandLower = brand.lowercase(Locale.getDefault())
    val backgroundColor = getCardColor(brandLower)
    val logo = getBrandLogo(brandLower)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1.586f)
            .background(backgroundColor, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        if (!mostrarDorso) {
            // FRENTE
            Column(Modifier.fillMaxSize()) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(65.dp)
                            .padding(end = 10.dp)
                    )
                }
                Spacer(Modifier.height(13.dp))

                Text(
                    text = if (number.isNotBlank()) number.chunked(4).joinToString(" ") else "•••• •••• •••• ••••",
                    color = White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.weight(1f))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = if (fullName.isNotBlank()) fullName.uppercase() else "NOMBRE Y APELLIDO",
                        color = White, style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (expirationDate.isNotBlank()) expirationDate else "MM/AA",
                        color = White, style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        } else {
            // DORSO
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(30.dp)
                        .background(Color.Black, RoundedCornerShape(7.dp))
                )
                Spacer(Modifier.height(29.dp))
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    Box(
                        Modifier
                            .width(80.dp)
                            .height(28.dp)
                            .background(Color.White, RoundedCornerShape(4.dp))
                            .padding(end = 10.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        // Espejado: Text será espejado si la tarjeta está girada!
                        Text(
                            text = if (cvv.isNotBlank()) cvv else "CVV",
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .then(
                                    Modifier.graphicsLayer {
                                        scaleX = -1f // invierte el texto para que se lea bien al girar la tarjeta
                                    }
                                )
                        )
                    }
                }
                Spacer(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun AgregarTarjetaScreen(modifier: Modifier = Modifier) {
    var number by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var expirationDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var cardType by remember { mutableStateOf(CardType.CREDITO) }
    var cvvFocused by remember { mutableStateOf(false) }
    val type = detectCardBrandFromNumber(number)

    val rotationY by animateFloatAsState(targetValue = if (cvvFocused) 180f else 0f, label = "card-rotation")
    val density = LocalDensity.current.density
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 32.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tarjeta visual, con giro animado (y proyección correcta 3D)
        Box(
            modifier = Modifier
                .graphicsLayer {
                   
                    cameraDistance = 12 * density
                    // Aplica scaleX=-1 SOLO cuando está de dorso, para espejar bien el texto atrás
                    if (rotationY > 90f) scaleX = -1f
                }
                .padding(bottom = 18.dp)
        ) {
            CreditCardPreview(
                number = number,
                fullName = fullName,
                expirationDate = expirationDate,
                brand = type,
                cvv = cvv,
                mostrarDorso = rotationY > 90f
            )
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(16.dp))
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = stringResource(R.string.agregarTarjeta),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 16.dp)
                )
                OutlinedTextField(
                    value = number,
                    onValueChange = { number = it },
                    label = {
                        Text(
                            text = stringResource(R.string.numero),
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(Modifier.height(13.dp))

                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = {
                        Text(
                            text = stringResource(R.string.nombre),
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(Modifier.height(13.dp))

                OutlinedTextField(
                    value = expirationDate,
                    onValueChange = { expirationDate = it },
                    label = {
                        Text(
                            text = stringResource(R.string.vencimiento),
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(Modifier.height(13.dp))

                OutlinedTextField(
                    value = cvv,
                    onValueChange = { cvv = it },
                    label = {
                        Text(
                            text = stringResource(R.string.CVV),
                            style = Typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onFocusChanged { focusState -> cvvFocused = focusState.isFocused },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = Black,
                        unfocusedLabelColor = Grey,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    )
                )

                Spacer(Modifier.height(18.dp))

                // --- RadioButtons Crédito/Débito ---
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    RadioButton(
                        selected = cardType == CardType.CREDITO,
                        onClick = { cardType = CardType.CREDITO }
                    )
                    Text(text = "Crédito", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.width(16.dp))
                    RadioButton(
                        selected = cardType == CardType.DEBITO,
                        onClick = { cardType = CardType.DEBITO }
                    )
                    Text(text = "Débito", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(Modifier.height(20.dp))

                Button(
                    onClick = {
                        val nuevaTarjeta = NewCardData(
                            type = detectCardBrandFromNumber(number),
                            number = number,
                            expirationDate = expirationDate,
                            fullName = fullName,
                            cvv = cvv
                            // Agrega aquí cardType si tu backend lo necesita
                        )
                        // falta llamada API
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = White
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.agregar),
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}