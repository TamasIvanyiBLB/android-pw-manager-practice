package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class CreateLoginSetupStep {
    LOGIN_NAME, PASSWORD, PASSWORD_REPEAT
}


@HiltViewModel
class CreateLoginViewModel @Inject constructor(
    private val accountsUseCases: AccountsUseCases,
    private val passwordUtils: PasswordUtils
) : ViewModel() {
    private val _setupStep = mutableStateOf(CreateLoginSetupStep.LOGIN_NAME)
    val setupStep: State<CreateLoginSetupStep> = _setupStep
    private val _createAccountUIState =
        mutableStateOf(CreateAccountUIStateVM(null, "", null, null, null, false))
    val createAccountUIState: State<CreateAccountUIStateVM> = _createAccountUIState

    fun getInputBoxValueBinding(step: CreateLoginSetupStep): String {
        return when (step) {
            CreateLoginSetupStep.LOGIN_NAME -> _createAccountUIState.value.login ?: ""
            CreateLoginSetupStep.PASSWORD -> _createAccountUIState.value.password ?: ""
            CreateLoginSetupStep.PASSWORD_REPEAT -> _createAccountUIState.value.passwordRepeat ?: ""
        }
    }

    fun setInputBoxValue(value: String, step: CreateLoginSetupStep) {
        when (step) {
            CreateLoginSetupStep.LOGIN_NAME -> _createAccountUIState.value =
                _createAccountUIState.value.copy(login = value)

            CreateLoginSetupStep.PASSWORD -> _createAccountUIState.value =
                _createAccountUIState.value.copy(password = value)

            CreateLoginSetupStep.PASSWORD_REPEAT -> _createAccountUIState.value =
                _createAccountUIState.value.copy(passwordRepeat = value)
        }
    }

    fun proceed(step: CreateLoginSetupStep): Boolean {
        return when (step) {
            CreateLoginSetupStep.LOGIN_NAME -> {
                _createAccountUIState.value = _createAccountUIState.value.copy(password = "")
                _setupStep.value = CreateLoginSetupStep.PASSWORD
                true
            }

            CreateLoginSetupStep.PASSWORD -> {
                _createAccountUIState.value = _createAccountUIState.value.copy(passwordRepeat = "")
                _setupStep.value = CreateLoginSetupStep.PASSWORD_REPEAT
                true
            }

            CreateLoginSetupStep.PASSWORD_REPEAT -> {
                if (_createAccountUIState.value.password != _createAccountUIState.value.passwordRepeat) {

                    false
                }
                true
            }
        }
    }
}