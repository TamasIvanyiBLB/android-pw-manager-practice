package com.example.androidpracticeapp_passwordmanager.views

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialDisplayItemViewModel

@Composable
fun PasswordDisplayContent(
    vm: CredentialDisplayItemViewModel,
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 5.dp)
    ) {
        Box(modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onDoubleTap = {
                clipboardManager.setText(AnnotatedString(vm.credential.value.password))
                Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
            })
        }) {
            if (vm.passwordVisible.value) {
                Text(
                    vm.credential.value.password,
                    color = colorResource(R.color.text),
                    fontWeight = FontWeight.Medium
                )
            } else {
                PasswordText(
                    vm.credential.value.password,
                )
            }
        }


        IconButton(
            onClick = { vm.setPasswordVisibility(!vm.passwordVisible.value) },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                modifier = Modifier.padding(horizontal = 5.dp),
                imageVector = if (vm.passwordVisible.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                contentDescription = null,
                tint = colorResource(R.color.text)
            )
        }
    }
}
