package com.example.androidpracticeapp_passwordmanager.viewmodels

data class CredentialVM(
    var id: Int,
    var title: String,
    var username: String?,
    var email: String?,
    var password: String
)