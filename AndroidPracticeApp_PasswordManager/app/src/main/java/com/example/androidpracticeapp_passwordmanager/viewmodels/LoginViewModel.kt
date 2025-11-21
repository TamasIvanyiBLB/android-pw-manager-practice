package com.example.androidpracticeapp_passwordmanager.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.mapper.AccountMapper
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val passwordUtils: PasswordUtils,
    private val accountsUseCases: AccountsUseCases,
    private val accountMapper: AccountMapper,
    private val loginContext: LoginContext
) : ViewModel() {
    private val _accountDropDownEnabled = mutableStateOf(false)
    val accountDropDownEnabled: State<Boolean> = _accountDropDownEnabled

    private val _canLogIn = mutableStateOf(false)
    val canLogIn: State<Boolean> = _canLogIn

    private val _errorText: MutableState<String?> = mutableStateOf(null)
    val errorText: State<String?> = _errorText

    private val _masterPasswordInput = mutableStateOf("")
    val masterPasswordInput: State<String> = _masterPasswordInput

    private val _selectedLogin: MutableState<AccountVM?> = mutableStateOf(null)
    val selectedLogin: State<AccountVM?> = _selectedLogin

    private val _logins: MutableState<List<AccountVM>> = mutableStateOf(emptyList())
    val logins: State<List<AccountVM>> = _logins

    init {
        loadAccounts()
    }

    fun activateAccountDropDown(value: Boolean? = null) {
        if (value == null) {
            _accountDropDownEnabled.value = !_accountDropDownEnabled.value
        } else {
            _accountDropDownEnabled.value = value
        }
    }

    fun selectLogin(value: AccountVM? = null) {
        _selectedLogin.value = value
        setCanLogIn()
    }

    fun updateMasterPassword(value: String) {
        _masterPasswordInput.value = value
        setCanLogIn()
    }

    fun tryLogin(invalidPasswordText: String): Boolean {
        if (_canLogIn.value && loginContext.tryAuthenticate(
                accountMapper.toEntity(selectedLogin.value!!),
                _masterPasswordInput.value
            )
        ) {
            Log.d("app", loginContext.authenticatedLogin.toString())
            return true
        } else {
            _errorText.value = invalidPasswordText
            return false
        }
    }

    fun clearErrorText() {
        _errorText.value = null
    }

    private fun setCanLogIn() {
        _canLogIn.value = _selectedLogin.value != null && !_masterPasswordInput.value.isEmpty()
    }

    private fun loadAccounts() {
        accountsUseCases.getAccounts().onEach { accounts ->
            _logins.value = accounts.map { accountMapper.fromEntity(it) }
            _selectedLogin.value = _logins.value.firstOrNull()
        }.launchIn(viewModelScope)
    }
}