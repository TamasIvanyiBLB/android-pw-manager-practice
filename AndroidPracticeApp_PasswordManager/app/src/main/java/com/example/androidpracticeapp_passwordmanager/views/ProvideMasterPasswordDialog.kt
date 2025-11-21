package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.ui.theme.PasswordInputTextField
import com.example.androidpracticeapp_passwordmanager.viewmodels.ProvideMasterPasswordDialogViewModel

@Composable
@Preview
fun ProvideMasterPasswordDialogPreview(){
    ProvideMasterPasswordDialog({},{},{})
}

@Composable
fun ProvideMasterPasswordDialog(onDismiss: () -> Unit, onSuccess: () -> Unit, onFail:(String)-> Unit, vm : ProvideMasterPasswordDialogViewModel = hiltViewModel()) {
    val invalidPasswordString = stringResource(R.string.invalidPassword)
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PasswordInputTextField(vm.masterPassword.value, R.string.provideMasterPassword,{vm.updateMasterPassword(it)},{vm.togglePasswordVisibility()},vm.passwordVisible.value)
                ElevatedButton({
                    if(vm.tryContinue()){
                        onSuccess()
                    }else{
                        onFail(invalidPasswordString)
                    }
                }, modifier = Modifier.padding(top = 10.dp), colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.button)
                )) {
                    Text(stringResource(R.string.continueStr), color = Color.White)
                }
            }
        }
    }
}