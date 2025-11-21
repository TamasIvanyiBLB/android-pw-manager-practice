package com.example.androidpracticeapp_passwordmanager.viewmodels

data class ProfileUIStateVM(
    val changePasswordActive: Boolean,
    val oldPasswordVisible: Boolean,
    val newPasswordVisible: Boolean,
    val newPasswordRepeatVisible: Boolean,
    val oldPasswordText: String,
    val newPasswordText: String,
    val newPasswordTextRepeat: String,
    val errorText: String?,
    val confirmDeleteVisible: Boolean,
    val deletionMasterPasswordDialogVisible: Boolean,
)