package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val accountsUseCases: AccountsUseCases,
    private val loginContext: LoginContext
) :
    ViewModel() {
    private val _hasLoginSetup = mutableStateOf(false)
    val hasLoginSetup: State<Boolean> = _hasLoginSetup

    init {
        getHasLoginSetup()
    }

    fun logout(navigateCallback: () -> Unit) {
        loginContext.logOut()
        navigateCallback()
    }

    private fun getHasLoginSetup() {
        viewModelScope.launch {
            _hasLoginSetup.value = accountsUseCases.getHasAccountSetUp()
        }
    }
}