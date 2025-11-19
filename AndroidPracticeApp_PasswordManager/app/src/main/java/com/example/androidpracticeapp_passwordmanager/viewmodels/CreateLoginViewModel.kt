package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.enums.CreateLoginSetupStep
import com.example.androidpracticeapp_passwordmanager.mapper.AccountMapper
import com.example.androidpracticeapp_passwordmanager.utils.CreateAccountErrorTexts
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateLoginViewModel @Inject constructor(
    private val accountsUseCases: AccountsUseCases,
    private val passwordUtils: PasswordUtils,
    private val accountMapper: AccountMapper,
) : ViewModel() {

    companion object {
        const val MIN_PASSWORD_LENGTH = 4
    }

    private var _accounts: List<AccountVM> = emptyList()
    private val _setupStep = mutableStateOf(CreateLoginSetupStep.LOGIN_NAME)
    val setupStep: State<CreateLoginSetupStep> = _setupStep
    private val _createAccountUIState =
        mutableStateOf(CreateAccountUIStateVM(null, "", null, null, null, false))
    val createAccountUIState: State<CreateAccountUIStateVM> = _createAccountUIState

    init {
        getAccounts()
    }

    fun switchPasswordVisible() {
        _createAccountUIState.value =
            _createAccountUIState.value.copy(showPassword = !_createAccountUIState.value.showPassword)
    }

    fun getAccounts() {
        accountsUseCases.getAccounts().onEach { accounts ->
            _accounts = accounts.map { accountMapper.fromEntity(it) }
        }.launchIn(viewModelScope)
    }

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

    fun clearError() {
        _createAccountUIState.value = _createAccountUIState.value.copy(errorText = null)
    }

    fun proceed(
        step: CreateLoginSetupStep,
        finishCallback: () -> Unit,
        errorTexts: CreateAccountErrorTexts
    ) {
        when (step) {
            CreateLoginSetupStep.LOGIN_NAME -> {
                if (_createAccountUIState.value.login.isNullOrEmpty()) {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(errorText = errorTexts.mustProvideLoginNameErrorText)
                } else if (_accounts.any { it.login == _createAccountUIState.value.login }) {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(errorText = errorTexts.loginNameExistsErrorText)
                } else {
                    _createAccountUIState.value = _createAccountUIState.value.copy(password = "")
                    _setupStep.value = CreateLoginSetupStep.PASSWORD
                }
            }

            CreateLoginSetupStep.PASSWORD -> {
                if (_createAccountUIState.value.password.isNullOrEmpty()) {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(errorText = errorTexts.mustProvidePasswordErrorText)
                } else if (_createAccountUIState.value.password!!.length < MIN_PASSWORD_LENGTH) {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(errorText = errorTexts.passwordLengthInvalidErrorText)
                } else {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(passwordRepeat = "")
                    _setupStep.value = CreateLoginSetupStep.PASSWORD_REPEAT
                }

            }

            CreateLoginSetupStep.PASSWORD_REPEAT -> {
                if (_createAccountUIState.value.password != _createAccountUIState.value.passwordRepeat) {
                    _createAccountUIState.value =
                        _createAccountUIState.value.copy(
                            passwordRepeat = null,
                            password = "",
                            errorText = errorTexts.passwordMisMatchErrorText
                        )
                    _setupStep.value = CreateLoginSetupStep.PASSWORD
                    return
                }
                val password = passwordUtils.hashPassword(_createAccountUIState.value.password!!)
                val account = accountMapper.toEntity(
                    AccountVM(
                        -1,
                        _createAccountUIState.value.login!!,
                        password
                    )
                )
                viewModelScope.launch {
                    accountsUseCases.createAccount(account)
                    finishCallback()
                }
            }
        }
    }
}