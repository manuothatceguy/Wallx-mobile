package ar.edu.itba.hci.wallx.ui.screens.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.theme.Info
import ar.edu.itba.hci.wallx.ui.theme.Interactive
import ar.edu.itba.hci.wallx.ui.theme.Primary
import ar.edu.itba.hci.wallx.ui.theme.SecondaryDarken1
import ar.edu.itba.hci.wallx.ui.theme.SurfaceLight
import ar.edu.itba.hci.wallx.ui.theme.Typography


@OptIn(InternalTextApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    wallXViewModel: WallXViewModel,
    onNavigateTo: (String) -> Unit
) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val uiState by wallXViewModel.uiState.collectAsState()
    var isVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    fun handleLogin() {
        wallXViewModel.login(
            user = user,
            password = password
        )
        if (uiState.error != null) {
            user = ""
            password = ""
        } else {
            errorMessage = null
            onNavigateTo(AppDestinations.DASHBOARD.route)
        }
    }
    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(70.dp)) // Esto sí va a separar verticalmente

            Row(
                modifier = modifier
                //padding(vertical = 60.dp),
            ) {
                Spacer(modifier = Modifier.heightIn(30.dp))
                Card(
                    modifier = modifier
                        .padding(horizontal = 120.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            modifier = Modifier.padding(16.dp),
                            style = Typography.titleLarge
                        )
                    }
                }
            }
            Row(modifier = modifier.padding(horizontal = 15.dp, vertical = 10.dp)) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Card(
                            modifier = Modifier
                                .padding(5.dp)
                                .height(270.dp)
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
                        ) {
                            Column(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Título centrado
                                Text(
                                    text = stringResource(R.string.inicio_de_sesión),
                                    style = Typography.titleLarge
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                    OutlinedTextField(
                                        value = user,
                                        onValueChange = { newUser -> user = newUser },
                                        placeholder = {
                                            Text(text = stringResource(R.string.usuario), color = Info)
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
                                            errorTextColor = MaterialTheme.colorScheme.error,

                                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = MaterialTheme.colorScheme.error.copy(
                                                alpha = 0.1f
                                            ),

                                            cursorColor = MaterialTheme.colorScheme.secondary,

                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                            errorBorderColor = MaterialTheme.colorScheme.error,

                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        singleLine = true,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Email,
                                            imeAction = ImeAction.Next
                                        ),
                                        keyboardActions = KeyboardActions(onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        })
                                    )


                                Spacer(modifier = Modifier.height(16.dp))

                                // Subtítulo y TextField: Contraseña

                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = { newPassword -> password = newPassword },
                                        placeholder = {
                                            Text(text = stringResource(R.string.contraseña), color = Info)
                                        },
                                        colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = MaterialTheme.colorScheme.primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
                                            errorTextColor = MaterialTheme.colorScheme.error,

                                            focusedContainerColor = MaterialTheme.colorScheme.secondary,
                                            unfocusedContainerColor = MaterialTheme.colorScheme.background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = MaterialTheme.colorScheme.error.copy(
                                                alpha = 0.1f
                                            ),

                                            cursorColor = MaterialTheme.colorScheme.secondary,

                                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = MaterialTheme.colorScheme.surfaceVariant,
                                            errorBorderColor = MaterialTheme.colorScheme.error,

                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        ),
                                        trailingIcon = {
                                            IconButton(onClick = { isVisible = !isVisible }) {
                                                Icon(
                                                    if (isVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                                    contentDescription = "",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        },
                                        singleLine = true,
                                        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Password,
                                            imeAction = ImeAction.Done
                                        ),
                                        keyboardActions = KeyboardActions(onDone = {
                                            focusManager.clearFocus()
                                            handleLogin()
                                        })
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = stringResource(R.string.olvidaste_contra),
                                        textDecoration = TextDecoration.Underline,
                                        //color = Secondary,
                                        fontSize = 13.sp,
                                        modifier = Modifier.clickable {
                                            //ir a la otra pantalla
                                        }

                                    )

                            }
                        }

                        Button(
                            onClick = { handleLogin() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,

                            )
                        ) {
                            Box {
                                Text(text = stringResource(R.string.inicio_sesion),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.no_tenés_una_cuenta),
                            textDecoration = TextDecoration.Underline,
                            //color = Info,
                            fontSize = 13.sp,
                            modifier = Modifier.clickable {
                                onNavigateTo(AppDestinations.REGISTRO.route)
                            }
                        )
                        Text(
                            text = stringResource(R.string.verificar_cuenta),
                            textDecoration = TextDecoration.Underline,
                            //color = Info,
                            fontSize = 13.sp,
                            modifier = Modifier.clickable {
                                onNavigateTo(AppDestinations.VERIFICAR.route)
                            }
                        )

                    }

                }
            }
        }
    }

}



