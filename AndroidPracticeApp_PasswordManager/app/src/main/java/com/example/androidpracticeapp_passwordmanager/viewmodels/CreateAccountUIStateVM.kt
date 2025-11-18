package com.example.androidpracticeapp_passwordmanager.viewmodels

data class CreateAccountUIStateVM(
    val id: Int?,
    val login: String?,
    val password: String?,
    val passwordRepeat: String?,
    val errorText: String?,
    val showPassword: Boolean,
)