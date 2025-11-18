package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialVM

@Composable
fun CredentialDeleteMenu(
    expanded: Boolean,
    onDismissRequest: (Boolean) -> Unit,
    onDelete: (CredentialVM) -> Unit,
    offset: DpOffset = DpOffset(0.dp, 0.dp),
    credential: CredentialVM
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest(false) },
        offset = offset,
        modifier = Modifier.background(colorResource(R.color.accent)),
        shape = RoundedCornerShape(16.dp)
    ) {
        DropdownMenuItem(
            onClick = {
                onDismissRequest(false)
                onDelete(credential)
            },
            text = {
                Text(
                    stringResource(R.string.delete),
                    color = colorResource(R.color.fail),
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                    modifier = Modifier.padding(0.dp)
                )
            }
        )
    }
}