package ar.edu.itba.hci.wallx.ui.screens.auth

import android.annotation.SuppressLint
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ar.edu.itba.hci.wallx.R
import ar.edu.itba.hci.wallx.WallXViewModel
import ar.edu.itba.hci.wallx.data.network.model.user.CredentialsData
import ar.edu.itba.hci.wallx.data.repository.UserRepository
import ar.edu.itba.hci.wallx.ui.navigation.AppDestinations
import ar.edu.itba.hci.wallx.ui.theme.Accent
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
import com.google.api.ResourceReference


@OptIn(InternalTextApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    wallXViewModel: WallXViewModel? = null,
    onNavigateTo: (String) -> Unit
) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = Error
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)) {
            Spacer(modifier = Modifier.height(70.dp)) // Esto sí va a separar verticalmente

            Row(
                modifier = modifier
                //padding(vertical = 60.dp),
            ) {
                Spacer(modifier= Modifier.heightIn(30.dp))
                Card(
                    modifier = modifier
                        .padding(horizontal = 120.dp, vertical = 16.dp)
                        .fillMaxWidth()
                        .height(60.dp)
                    //colors = CardDefaults.cardColors(containerColor = Secondary)
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
            Row(modifier = modifier.padding(horizontal = 30.dp, vertical = 10.dp)) {
                Card(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 10.dp)
                    //colors = CardDefaults.cardColors(containerColor = Secondary)
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
                                .height(300.dp)
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(containerColor = Primary)
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
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.usuario),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = user,
                                        onValueChange = { newUser -> user = newUser },
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.usuario),
                                                color = Info
                                            )
                                        },
                                        /*colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,

                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),

                                            cursorColor = Secondary,

                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,

                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        )*/

                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Subtítulo y TextField: Contraseña
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = stringResource(R.string.contraseña),
                                        style = Typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    OutlinedTextField(
                                        value = password,
                                        onValueChange = { newPassword -> password = newPassword },
                                        placeholder = {
                                            Text(
                                                text = stringResource(R.string.contraseña),
                                                color = Info
                                            )
                                        },
                                        /*colors = OutlinedTextFieldDefaults.colors(
                                            focusedTextColor = Primary,
                                            unfocusedTextColor = Info,
                                            disabledTextColor = SurfaceVariant,
                                            errorTextColor = Error,

                                            focusedContainerColor = Secondary,
                                            unfocusedContainerColor = Background,
                                            disabledContainerColor = SurfaceLight,
                                            errorContainerColor = Error.copy(alpha = 0.1f),

                                            cursorColor = Secondary,

                                            focusedBorderColor = SecondaryDarken1,
                                            unfocusedBorderColor = Interactive,
                                            disabledBorderColor = SurfaceVariant,
                                            errorBorderColor = Error,

                                            focusedLeadingIconColor = SecondaryDarken1,
                                            unfocusedLeadingIconColor = Interactive.copy(alpha = 0.7f),

                                            focusedTrailingIconColor = SecondaryDarken1,
                                            unfocusedTrailingIconColor = Interactive.copy(alpha = 0.7f),
                                        )*/
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = stringResource(R.string.olvidaste_contra),
                                        textDecoration = TextDecoration.Underline,
                                        //color = Secondary,
                                        fontSize = 13.sp,
                                        modifier = Modifier.clickable{
                                            //ir a la otra pantalla
                                        }

                                        )
                                }
                            }
                        }
                        Button(
                            onClick = {
                                wallXViewModel?.login(
                                    user = user,
                                    password = password
                                )
                                wallXViewModel?.getUser()
                                onNavigateTo(AppDestinations.DASHBOARD.route)

                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                                .height(50.dp),
                            /*colors = ButtonDefaults.buttonColors(
                                containerColor = Accent,
                                contentColor = White
                            )*/
                        ){
                            Box{
                                Text(text=stringResource(R.string.inicio_sesion))
                            }
                        }
                        Text(
                            text = stringResource(R.string.no_tenés_una_cuenta),
                            textDecoration = TextDecoration.Underline,
                            //color = Info,
                            fontSize = 13.sp,
                            modifier = Modifier.clickable{
                                onNavigateTo(AppDestinations.REGISTRO.route)
                            }
                        )

                    }

                }
            }
            if (errorMessage != null) {
                LaunchedEffect(errorMessage) {
                    snackbarHostState.showSnackbar(errorMessage!!)
                    errorMessage = null
                }
            }
        }
    }
}

