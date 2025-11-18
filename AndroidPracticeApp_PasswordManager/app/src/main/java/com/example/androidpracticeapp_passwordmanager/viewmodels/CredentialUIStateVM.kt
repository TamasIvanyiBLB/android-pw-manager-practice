package com.example.androidpracticeapp_passwordmanager.viewmodels

data class CredentialUIStateVM(
    val title: String = "",
    val username: String? = null,
    val password: String = "",
    val email: String? = null,
    val passwordLengthStr: String = "",
    val useSpecialCharacters: Boolean = false
)