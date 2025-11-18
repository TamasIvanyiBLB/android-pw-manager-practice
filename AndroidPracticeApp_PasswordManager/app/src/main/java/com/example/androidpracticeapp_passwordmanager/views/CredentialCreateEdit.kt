package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialCreateEditViewModel

@Composable
fun CredentialCreateEdit(
    navController: NavController,
    vm: CredentialCreateEditViewModel = hiltViewModel()
) {
    val textColor = colorResource(R.color.text)
    TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = textColor
    )

    OutlinedTextFieldDefaults.colors(
        focusedBorderColor = colorResource(R.color.primary),
        unfocusedBorderColor = colorResource(R.color.text)
    )

    TextStyle(
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = textColor
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
            .border(1.dp, colorResource(R.color.primary), RoundedCornerShape(16.dp)),
        colors = CardDefaults.elevatedCardColors(
            containerColor = colorResource(R.color.bg)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(Modifier.padding(vertical = 10.dp)) {

            StyledTitle(stringResource(R.string.title))
            StyledTextInput(vm.formUIState.value.title) { value -> vm.updateTitle(value) }

            StyledTitle(stringResource(R.string.username))
            StyledTextInput(
                vm.formUIState.value.username ?: ""
            ) { value -> vm.updateUsername(value) }

            StyledTitle(stringResource(R.string.email))
            StyledTextInput(vm.formUIState.value.email ?: "") { value -> vm.updateEmail(value) }

            StyledTitle(stringResource(R.string.password))
            StyledTextInput(vm.formUIState.value.password) { value -> vm.updatePassword(value) }

            HorizontalDivider(color = colorResource(R.color.primary))
            StyledSubTitle(stringResource(R.string.password_generation_settings))
            Row {
                Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        StyledSubText(stringResource(R.string.length))
                        MarginlessTextField(vm.formUIState.value.passwordLengthStr) { value ->
                            vm.updatePasswordLength(
                                value
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.padding(top = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        StyledSubText(stringResource(R.string.use_special_characters))
                        Checkbox(
                            onCheckedChange = { value -> vm.onUseSpecialCharacterChange(value) },
                            checked = vm.formUIState.value.useSpecialCharacters,
                            colors = CheckboxDefaults.colors(
                                checkedColor = colorResource(R.color.primary),
                                checkmarkColor = Color.White,
                                uncheckedColor = colorResource(R.color.text)
                            )
                        )
                    }
                }
                IconButton(
                    onClick = { vm.onGeneratePassword() },
                    modifier = Modifier
                        .width(64.dp)
                        .height(64.dp)
                        .padding(0.dp)
                ) {
                    Icon(
                        Icons.Filled.Autorenew,
                        tint = colorResource(R.color.primary),
                        contentDescription = stringResource(R.string.generate),
                        modifier = Modifier
                            .width(38.dp)
                            .height(38.dp)
                    )
                }
            }

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
                ElevatedButton(
                    onClick = {
                        vm.onSave()
                        navController.navigate(Screen.CredentialsListScreen.route)
                    }, colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = colorResource(R.color.button)
                    )
                ) {
                    Text(

                        stringResource(R.string.save), color = colorResource(R.color.bg),
                        style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarginlessTextField(bindingValue: String, onValueChange: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = bindingValue,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text),
            textAlign = TextAlign.Center
        ),
        decorationBox = { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = bindingValue,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                interactionSource = interactionSource,
                contentPadding = PaddingValues(0.dp), // 👈 removes all inner padding
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Black,
                    unfocusedIndicatorColor = Color.Gray,
                    disabledIndicatorColor = Color.LightGray
                )
            )
        })
}

@Composable
fun StyledSubText(textValue: String) {
    Text(
        textValue,
        modifier = Modifier
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Left,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text)
        )
    )
}

@Composable
fun StyledTitle(textValue: String) {
    Text(
        textValue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Left,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text)
        )
    )
}

@Composable
fun StyledSubTitle(textValue: String) {
    Text(
        textValue,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .padding(bottom = 20.dp)
            .padding(horizontal = 10.dp),
        textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.text)
        )
    )
}

@Composable
fun StyledTextInput(bindingValue: String, textChangedCallback: (String) -> Unit) {
    TextField(
        onValueChange = textChangedCallback,
        value = bindingValue,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = colorResource(R.color.primary),
            unfocusedBorderColor = colorResource(R.color.text)
        ),
        textStyle = TextStyle(
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = colorResource(R.color.text)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    )
}