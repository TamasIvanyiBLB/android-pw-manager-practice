package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.androidpracticeapp_passwordmanager.R

@Composable
@Preview
fun ConfirmDialogPreview(){
    ConfirmDialog("Message", {}, {})
}

@Composable
fun ConfirmDialog(
    message: String,
    onAccept: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onCancel() },
        title = {
            Text(
                stringResource(R.string.warning),
                color = colorResource(R.color.accent),
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                TextButton(onClick = { onAccept() }) {
                    Text(
                        stringResource(R.string.yes),
                        color = colorResource(R.color.text),
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    )
                }

                TextButton(onClick = { onCancel() }) {
                    Text(
                        stringResource(R.string.no),
                        color = colorResource(R.color.fail),
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    )
                }
            }

        }
    )
}