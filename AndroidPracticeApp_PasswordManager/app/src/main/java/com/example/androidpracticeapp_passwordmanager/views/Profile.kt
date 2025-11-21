package com.example.androidpracticeapp_passwordmanager.views

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.ui.theme.PasswordInputTextField
import com.example.androidpracticeapp_passwordmanager.utils.EditProfileErrorTexts
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.CreateLoginViewModel
import com.example.androidpracticeapp_passwordmanager.viewmodels.ProfileViewModel

@Composable
fun Profile(navController: NavController, vm: ProfileViewModel = hiltViewModel()) {
    val errorTexts = EditProfileErrorTexts(
        stringResource(R.string.mustProvideOldPassword),
        stringResource(R.string.mustProvideNewPassword),
        stringResource(R.string.mustRepeatNewPassword),
        stringResource(R.string.passwordMisMatch),
        stringResource(R.string.oldPasswordIncorrect),
        String.format(
            stringResource(R.string.invalidPasswordLength),
            CreateLoginViewModel.MIN_PASSWORD_LENGTH
        ),
    )
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Icon(
                    Icons.Filled.AccountCircle,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(128.dp)
                        .padding(top = 40.dp),
                    contentDescription = null,
                    tint = colorResource(
                        R.color.text
                    )
                )
                Text(
                    vm.accountName,
                    modifier = Modifier.fillMaxWidth(),
                    style = TextStyle(
                        color = colorResource(R.color.text),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                )
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {

                if (!vm.profileUIState.value.changePasswordActive) {
                    ElevatedButton(
                        onClick = { vm.togglePasswordUpdate() },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.button)
                        )
                    ) {
                        Text(stringResource(R.string.changePassword), color = Color.White)
                    }
                } else {
                    Column(verticalArrangement = Arrangement.SpaceAround) {
                        PasswordInputTextField(
                            vm.profileUIState.value.oldPasswordText,
                            R.string.oldPassword,
                            { vm.updateOldPasswordText(it) },
                            { vm.switchOldPasswordInputVisibility() },
                            vm.profileUIState.value.oldPasswordVisible
                        )
                        PasswordInputTextField(
                            vm.profileUIState.value.newPasswordText,
                            R.string.newPassword,
                            { vm.updateNewPasswordText(it) },
                            { vm.switchNewPasswordInputVisibility() },
                            vm.profileUIState.value.newPasswordVisible
                        )
                        PasswordInputTextField(
                            vm.profileUIState.value.newPasswordTextRepeat,
                            R.string.repeatNewPassword,
                            { vm.updateNewPasswordRepeatText(it) },
                            { vm.switchNewPasswordRepeatInputVisibility() },
                            vm.profileUIState.value.newPasswordRepeatVisible
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp)
                                .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val context = LocalContext.current
                            ElevatedButton(
                                onClick = {
                                    vm.updatePassword(errorTexts) {
                                        Toast.makeText(
                                            context,
                                            R.string.passwordUpdated,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.button)
                                )
                            ) {
                                Text(
                                    stringResource(R.string.save),
                                    color = colorResource(R.color.bg)
                                )
                            }

                            ElevatedButton(
                                onClick = { vm.cancelUpdatePassword() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = colorResource(R.color.accent)
                                )
                            ) {
                                Text(
                                    stringResource(R.string.cancel),
                                    color = colorResource(R.color.text)
                                )
                            }
                        }
                    }
                }
            }

            Column(horizontalAlignment = Alignment.End) {
                ElevatedButton(
                    onClick = {
                        vm.logOut()
                        navController.navigate(Screen.LoginScreen.route)
                    }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.accent)
                    )
                ) {
                    Text(stringResource(R.string.logOut), color = colorResource(R.color.text))
                }

                Text(
                    stringResource(R.string.deleteProfile), style = TextStyle(
                        color = colorResource(R.color.fail),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal
                    ), modifier = Modifier
                        .padding(top = 20.dp)
                        .clickable(onClick = {
                            vm.showConfirmDelete(true)

                        })
                )
            }
        }

        if (vm.profileUIState.value.errorText != null) {
            ErrorDialog(vm.profileUIState.value.errorText ?: "") { vm.clearError() }
        }

        if (vm.profileUIState.value.confirmDeleteVisible) {
            ConfirmDialog(
                stringResource(R.string.areYouSureToDeleteProfile),
                onAccept = {
                    vm.showMasterPasswordForDeletionVisible(true)
                },
                onCancel = {
                    vm.showConfirmDelete(false)
                })
        }

        if (vm.profileUIState.value.deletionMasterPasswordDialogVisible) {
            ProvideMasterPasswordDialog(
                {
                    vm.showMasterPasswordForDeletionVisible(false)
                },
                {
                    vm.showMasterPasswordForDeletionVisible(false)
                    vm.deleteProfile {
                        navController.navigate(Screen.LoginScreen.route)
                    }
                },
                {
                    vm.showMasterPasswordForDeletionVisible(false)
                    vm.setError(it)
                }
            )
        }
    }
}