package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ProvideMasterPasswordDialogViewModel @Inject constructor(
    private val loginContext: LoginContext,
    private val passwordUtils: PasswordUtils
) : ViewModel() {
    private val _masterPassword = mutableStateOf("")
    val masterPassword : State<String> = _masterPassword
    private val _passwordVisible = mutableStateOf(false)
    val passwordVisible : State<Boolean> = _passwordVisible
    fun updateMasterPassword(value:String){
        _masterPassword.value = value
    }

    fun togglePasswordVisibility(){
        _passwordVisible.value = !_passwordVisible.value
    }

    fun tryContinue(): Boolean{
        if(_masterPassword.value.isEmpty()){
            return false
        }

        return passwordUtils.comparePassword(loginContext.authenticatedLogin!!.password, _masterPassword.value)
    }
}