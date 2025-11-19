package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.androidpracticeapp_passwordmanager.R

@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                stringResource(R.string.error),
                color = colorResource(R.color.fail),
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            )
        },
        text = {
            Text(
                message,
                color = colorResource(R.color.text),
                style = TextStyle(fontSize = 16.sp)
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(R.string.ok),
                    color = colorResource(R.color.text),
                    style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                )
            }
        }
    )
}