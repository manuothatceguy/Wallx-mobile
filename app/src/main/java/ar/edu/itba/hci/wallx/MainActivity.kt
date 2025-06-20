package ar.edu.itba.hci.wallx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ar.edu.itba.hci.wallx.ui.screens.MainApp
import ar.edu.itba.hci.wallx.ui.theme.WallxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallxTheme {
                MainApp()
            }
        }
    }
}