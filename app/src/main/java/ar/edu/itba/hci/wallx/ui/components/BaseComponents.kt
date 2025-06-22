package ar.edu.itba.hci.wallx.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun WallXButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: ImageVector? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    contentPadding: PaddingValues = ButtonDefaults.ButtonWithIconContentPadding
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        contentPadding = contentPadding,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun WallXOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: ImageVector? = null
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(8.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun WallXTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String,
    icon: ImageVector? = null
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Composable
fun WallXOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    placeholder: String? = null,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    isError: Boolean = false,
    errorMessage: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    visualTransformation: VisualTransformation
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            label = label?.let { { Text(text = it) } },
            placeholder = placeholder?.let { { Text(text = it) } },
            leadingIcon = leadingIcon?.let { { Icon(imageVector = it, contentDescription = null) } },
            trailingIcon = trailingIcon?.let { { Icon(imageVector = it, contentDescription = null) } },
            isError = isError,
            singleLine = singleLine,
            enabled = enabled,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorLabelColor = MaterialTheme.colorScheme.error
            ),
            visualTransformation = visualTransformation
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun WallXCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        content()
    }
}

@Composable
fun WallXElevatedCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        content()
    }
}

@Composable
fun WallXIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    contentDescription: String? = null,
    tint: Color = MaterialTheme.colorScheme.onSurface
) {
    IconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun WallXFilledIconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector,
    contentDescription: String? = null,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            modifier = Modifier.size(24.dp)
        )
    }
}

// Material 3 Text Components
@Composable
fun WallXText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color
    )
}

@Composable
fun WallXTitle(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun WallXHeadline(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        fontWeight = FontWeight.Bold
    )
}

// Material 3 Divider Component
@Composable
fun WallXDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
    thickness: androidx.compose.ui.unit.Dp = 1.dp
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness
    )
}

// Material 3 Spacer Components
@Composable
fun WallXSpacer(
    modifier: Modifier = Modifier
) {
    Spacer(modifier = modifier)
}

@Composable
fun WallXVerticalSpacer(
    height: androidx.compose.ui.unit.Dp = 16.dp
) {
    Spacer(modifier = Modifier.height(height))
}

@Composable
fun WallXHorizontalSpacer(
    width: androidx.compose.ui.unit.Dp = 16.dp
) {
    Spacer(modifier = Modifier.width(width))
} 