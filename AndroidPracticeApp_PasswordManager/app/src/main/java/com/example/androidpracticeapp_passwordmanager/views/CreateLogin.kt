package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.enums.CreateLoginSetupStep
import com.example.androidpracticeapp_passwordmanager.utils.CreateAccountErrorTexts
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.CreateLoginViewModel

@Composable
@Preview(showBackground = true)
fun CreateLoginPreview() {
    CreateLogin(rememberNavController())
}

@Composable
fun CreateLogin(navController: NavController, vm: CreateLoginViewModel = hiltViewModel()) {
    val createAccountErrorTexts = CreateAccountErrorTexts(
        stringResource(R.string.mustProvideLoginName),
        stringResource(R.string.loginNameExists),
        stringResource(R.string.passwordMisMatch),
        stringResource(R.string.mustProvidePassword),
        String.format( stringResource(R.string.invalidPasswordLength), CreateLoginViewModel.MIN_PASSWORD_LENGTH)
    )
    Box(){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {

            Text(
                stringResource(R.string.setUpLogin), style = TextStyle(
                    color = colorResource(R.color.text),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(bottom = 60.dp)
            )

            if(vm.setupStep.value != CreateLoginSetupStep.LOGIN_NAME){
                OutlinedTextField(
                    value = vm.getInputBoxValueBinding(vm.setupStep.value),
                    onValueChange = { vm.setInputBoxValue(it, vm.setupStep.value) },
                    label = {
                        Text(
                            when (vm.setupStep.value) {
                                CreateLoginSetupStep.LOGIN_NAME -> stringResource(R.string.provideLoginName)
                                CreateLoginSetupStep.PASSWORD -> stringResource(R.string.provideMasterPassword)
                                CreateLoginSetupStep.PASSWORD_REPEAT -> stringResource(R.string.repeatMasterPassword)
                            },
                            style = TextStyle(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    visualTransformation = if (vm.createAccountUIState.value.showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.primary),
                        unfocusedBorderColor = colorResource(R.color.text),
                        focusedTrailingIconColor = colorResource(R.color.primary),
                        unfocusedTrailingIconColor = colorResource(R.color.text),
                        focusedLabelColor = colorResource(R.color.primary),
                        unfocusedLabelColor = colorResource(R.color.text),
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(horizontal = 20.dp),
                    trailingIcon = {
                        IconButton(onClick = { vm.switchPasswordVisible() }) {
                            Icon(
                                imageVector = if (vm.createAccountUIState.value.showPassword)
                                    Icons.Default.Visibility
                                else
                                    Icons.Default.VisibilityOff,
                                contentDescription = null, tint = colorResource(R.color.text)
                            )
                        }
                    }
                )
            }else{
                OutlinedTextField(
                    value = vm.getInputBoxValueBinding(vm.setupStep.value),
                    onValueChange = { vm.setInputBoxValue(it, vm.setupStep.value) },
                    label = {
                        Text(
                            when (vm.setupStep.value) {
                                CreateLoginSetupStep.LOGIN_NAME -> stringResource(R.string.provideLoginName)
                                CreateLoginSetupStep.PASSWORD -> stringResource(R.string.provideMasterPassword)
                                CreateLoginSetupStep.PASSWORD_REPEAT -> stringResource(R.string.repeatMasterPassword)
                            },
                            style = TextStyle(fontWeight = FontWeight.SemiBold)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.primary),
                        unfocusedBorderColor = colorResource(R.color.text),
                        focusedTrailingIconColor = colorResource(R.color.primary),
                        unfocusedTrailingIconColor = colorResource(R.color.text),
                        focusedLabelColor = colorResource(R.color.primary),
                        unfocusedLabelColor = colorResource(R.color.text),
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(horizontal = 20.dp),
                )
            }
            ElevatedButton(
                onClick = {
                    vm.proceed(
                        vm.setupStep.value,
                        { navController.navigate(Screen.LoginScreen.route) },
                        createAccountErrorTexts)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 10.dp),
                colors = ButtonDefaults.elevatedButtonColors(containerColor = colorResource(R.color.button))
            ) {
                Text(
                    when (vm.setupStep.value) {
                        CreateLoginSetupStep.LOGIN_NAME -> stringResource(R.string.continueStr)
                        CreateLoginSetupStep.PASSWORD -> stringResource(R.string.continueStr)
                        CreateLoginSetupStep.PASSWORD_REPEAT -> stringResource(R.string.finish)
                    },
                    color = Color.White,
                    style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                )
            }


        }
        if(!vm.createAccountUIState.value.errorText.isNullOrEmpty()){

            ErrorDialog(vm.createAccountUIState.value.errorText!!) { vm.clearError() }
        }

    }
}

