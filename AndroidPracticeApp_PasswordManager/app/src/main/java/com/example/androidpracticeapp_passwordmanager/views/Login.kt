package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.LoginViewModel

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    Login(rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController, vm: LoginViewModel = hiltViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = vm.accountDropDownEnabled.value,
            onExpandedChange = {},
            modifier = Modifier
                .padding(bottom = 20.dp)
                .background(Color.Transparent)
        ) {
            OutlinedTextField(
                value = vm.selectedLogin.value?.login?:"",
                onValueChange ={ },
                label = {
                    Text(
                        stringResource(R.string.selectUser),
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
                    .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                    .background(Color.Transparent)
                    .clickable { vm.activateAccountDropDown() },
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = if (vm.accountDropDownEnabled.value)
                                Icons.Default.KeyboardArrowUp
                            else
                                Icons.Default.KeyboardArrowDown,
                            contentDescription = null, tint = colorResource(R.color.text)
                        )
                    }
                }
            )

            ExposedDropdownMenu(
                expanded = vm.accountDropDownEnabled.value,
                onDismissRequest = { vm.activateAccountDropDown(false) },
                containerColor = colorResource(R.color.bg)
            ) {
                vm.logins.value.forEach { login ->
                    DropdownMenuItem(
                        text = { Text(login.login, color = colorResource(R.color.text)) },
                        onClick = {
                            vm.selectLogin(login)
                        }
                    )
                }
            }
        }


        OutlinedTextField(
            "", onValueChange = {}, modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    stringResource(R.string.password),
                    color = colorResource(R.color.placeholder)
                )
            },
            textStyle = TextStyle(
                color = colorResource(R.color.text),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            ), colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.primary),
                unfocusedBorderColor = colorResource(R.color.text)
            )
        )
        ElevatedButton(
            onClick = {
                navController.navigate(
                    Screen.CredentialCreateEditScreen.createRoute(
                        null
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.elevatedButtonColors(containerColor = colorResource(R.color.button))
        ) {
            Text(
                stringResource(R.string.login),
                color = Color.White,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }
        ElevatedButton(
            onClick = {
                navController.navigate(
                    Screen.CredentialCreateEditScreen.createRoute(
                        null
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.elevatedButtonColors(containerColor = colorResource(R.color.accent))
        ) {
            Text(
                stringResource(R.string.createNewCredential),
                color = colorResource(R.color.text),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}