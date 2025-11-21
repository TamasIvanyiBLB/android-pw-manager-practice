package com.example.androidpracticeapp_passwordmanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.androidpracticeapp_passwordmanager.R

@Composable
fun PasswordInputTextField(
    valueBinding: String?,
    labelResourceId: Int,
    onValueChange: (String) -> Unit,
    onVisibilityChangeClick: () -> Unit,
    visibilityState: Boolean
) {
    OutlinedTextField(
        value = valueBinding ?: "",
        onValueChange = onValueChange,
        label = {
            Text(
                stringResource(labelResourceId),
                style = TextStyle(fontWeight = FontWeight.SemiBold)
            )
        },
        visualTransformation = if (visibilityState) VisualTransformation.None else PasswordVisualTransformation(),
        colors = DefaultTextFieldColors(),
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(horizontal = 20.dp),
        trailingIcon = {
            IconButton(onClick = onVisibilityChangeClick) {
                Icon(
                    imageVector = if (visibilityState)
                        Icons.Default.Visibility
                    else
                        Icons.Default.VisibilityOff,
                    contentDescription = null, tint = colorResource(R.color.text)
                )
            }
        }
    )
}