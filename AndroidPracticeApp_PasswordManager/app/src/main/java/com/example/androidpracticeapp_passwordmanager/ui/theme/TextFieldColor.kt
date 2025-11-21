package com.example.androidpracticeapp_passwordmanager.ui.theme

import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.example.androidpracticeapp_passwordmanager.R

@Composable
fun DefaultTextFieldColors() : TextFieldColors {
    return OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.primary),
        unfocusedBorderColor = colorResource(R.color.text),
        focusedTrailingIconColor = colorResource(R.color.primary),
        unfocusedTrailingIconColor = colorResource(R.color.text),
        focusedLabelColor = colorResource(R.color.primary),
        unfocusedLabelColor = colorResource(R.color.text),
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent
    )
}

