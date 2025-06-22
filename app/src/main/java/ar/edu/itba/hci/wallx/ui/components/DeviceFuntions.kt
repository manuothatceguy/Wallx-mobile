package ar.edu.itba.hci.wallx.ui.components

import android.content.res.Configuration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.runtime.Composable

@Composable
fun isTablet(configuration: Configuration): Boolean {
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    return screenWidth >= 600 && screenHeight >= 600
}

@Composable
fun isHorizontal(configuration: Configuration): Boolean {
    return configuration.screenWidthDp > configuration.screenHeightDp
}

@Composable
fun DeviceLayout(
    phoneVertical: @Composable () -> Unit,
    phoneHorizontal: @Composable () -> Unit,
    tabletVertical: @Composable () -> Unit,
    tabletHorizontal: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    when {
        isTablet(configuration) && isHorizontal(configuration) -> {
            tabletHorizontal()
        }
        isTablet(configuration) -> {
            tabletVertical()
        }
        isHorizontal(configuration) -> {
            phoneHorizontal()
        }
        else -> {
            phoneVertical()
        }
    }
}
