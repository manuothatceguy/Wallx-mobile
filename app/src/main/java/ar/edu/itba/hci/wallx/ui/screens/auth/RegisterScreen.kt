package ar.edu.itba.hci.wallx.ui.screens.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.edu.itba.hci.wallx.ui.components.SimpleTextInputComponent
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.Secondary
import ar.edu.itba.hci.wallx.ui.theme.Typography
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme

@OptIn(InternalTextApi::class)
@Composable
fun RegisterScreen(
    modifier: Modifier
) {}//{
//    Column(modifier = modifier.fillMaxSize()) {
//        Row(
//            modifier = modifier,
//        ) {
//            Card(
//                modifier = modifier
//                    .padding(horizontal = 120.dp, vertical = 16.dp)
//                    .fillMaxWidth()
//                    .height(60.dp),
//                colors = CardDefaults.cardColors(containerColor = Secondary)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "WallX",
//                        modifier = Modifier.padding(16.dp),
//                        style = Typography.titleLarge
//                    )
//                }
//            }
//        }
//        Row(modifier = modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
//            Card(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 30.dp, vertical = 10.dp)
//                    .heightIn(min = 300.dp), // opcional: para que se ajuste al contenido
//                colors = CardDefaults.cardColors(containerColor = Secondary)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//
//                ) {
//                    Card(
//                        modifier = Modifier
//                            .padding(5.dp)
//                            .height(300.dp)
//                            .fillMaxSize(),
//                        colors = CardDefaults.cardColors(containerColor = Primary)
//                    ) {
//                        Column(
//
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            // Título centrado
//                            Text(
//                                text = "Inicio de sesión",
//                                style = Typography.titleLarge
//                            )
//
//                            // Subtítulo y TextField: Usuario
//                            Column(modifier = Modifier.fillMaxWidth()) {
//                                Text(
//                                    text = "Usuario",
//                                    style = Typography.bodyMedium,
//                                    modifier = Modifier.padding(bottom = 4.dp)
//                                )
//                                TextField(
//                                    value = "",
//                                    onValueChange = {},
//                                    modifier = Modifier.fillMaxWidth(),
//                                    singleLine = true
//                                )
//                            }
//
//                            Spacer(modifier = Modifier.height(16.dp))
//
//                            // Subtítulo y TextField: Contraseña
//                            Column(modifier = Modifier.fillMaxWidth()) {
//                                Text(
//                                    text = "Contraseña",
//                                    style = Typography.bodyMedium,
//                                    modifier = Modifier.padding(bottom = 4.dp)
//                                )
//                                SimpleTextInputComponent()
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun RegisterScreenPreview() {
//    WallxTheme(dynamicColor = false) {
//        RegisterScreen(modifier = Modifier)
//    }
//}

