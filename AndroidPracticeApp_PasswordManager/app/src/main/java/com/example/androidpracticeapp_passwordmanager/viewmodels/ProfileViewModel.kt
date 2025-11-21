package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.CredentialsUseCases
import com.example.androidpracticeapp_passwordmanager.utils.EditProfileErrorTexts
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val loginContext: LoginContext,
    private val accountsUseCases: AccountsUseCases,
    private val credentialsUseCases: CredentialsUseCases,
    private val passwordUtils: PasswordUtils
) : ViewModel() {
    private val _profileUIState = mutableStateOf(
        ProfileUIStateVM(
            changePasswordActive = false,
            oldPasswordVisible = false,
            newPasswordVisible = false,
            newPasswordRepeatVisible = false,
            oldPasswordText = "",
            newPasswordText = "",
            newPasswordTextRepeat = "",
            errorText = null,
            confirmDeleteVisible = false,
            deletionMasterPasswordDialogVisible = false
        )
    )
    val accountName get() = loginContext.authenticatedLogin!!.login
    val profileUIState: State<ProfileUIStateVM> = _profileUIState

    fun updateOldPasswordText(value: String) {
        _profileUIState.value = _profileUIState.value.copy(oldPasswordText = value)
    }

    fun updateNewPasswordText(value: String) {
        _profileUIState.value = _profileUIState.value.copy(newPasswordText = value)
    }

    fun updateNewPasswordRepeatText(value: String) {
        _profileUIState.value = _profileUIState.value.copy(newPasswordTextRepeat = value)
    }

    fun switchOldPasswordInputVisibility() {
        _profileUIState.value =
            _profileUIState.value.copy(oldPasswordVisible = !_profileUIState.value.oldPasswordVisible)
    }

    fun switchNewPasswordInputVisibility() {
        _profileUIState.value =
            _profileUIState.value.copy(newPasswordVisible = !_profileUIState.value.newPasswordVisible)
    }

    fun switchNewPasswordRepeatInputVisibility() {
        _profileUIState.value =
            _profileUIState.value.copy(newPasswordRepeatVisible = !_profileUIState.value.newPasswordRepeatVisible)
    }

    fun togglePasswordUpdate() {
        _profileUIState.value = _profileUIState.value.copy(changePasswordActive = true)
    }

    fun updatePassword(errorTexts: EditProfileErrorTexts, successCallback: () -> Unit) {
        if (_profileUIState.value.oldPasswordText.isEmpty()) {
            setError(errorTexts.mustProvideOldPasswordErrorText)
            return
        }
        if (_profileUIState.value.newPasswordText.isEmpty()) {
            setError(errorTexts.mustProvideNewPasswordErrorText)
            return
        }
        if (_profileUIState.value.newPasswordTextRepeat.isEmpty()) {
            setError(errorTexts.mustRepeatNewPasswordErrorText)
            return
        }
        if (!passwordUtils.comparePassword(
                loginContext.authenticatedLogin!!.password,
                _profileUIState.value.oldPasswordText
            )
        ) {
            setError(errorTexts.oldPasswordIncorrectErrorText)
            _profileUIState.value = _profileUIState.value.copy(oldPasswordText = "")
            return
        }
        if (_profileUIState.value.newPasswordText.length < CreateLoginViewModel.MIN_PASSWORD_LENGTH) {
            setError(errorTexts.passwordInvalidLengthErrorText)
            return
        }
        if (_profileUIState.value.newPasswordText != _profileUIState.value.newPasswordTextRepeat) {
            setError(errorTexts.passwordMisMatchErrorText)
            return
        }

        viewModelScope.launch {
            val hashedPassword = passwordUtils.hashPassword(_profileUIState.value.newPasswordText)
            accountsUseCases.updateAccountPassword(
                loginContext.authenticatedLogin!!.id!!,
                hashedPassword
            )
            _profileUIState.value = _profileUIState.value.copy(
                newPasswordText = "",
                oldPasswordText = "",
                newPasswordTextRepeat = "",
                newPasswordVisible = false,
                oldPasswordVisible = false,
                newPasswordRepeatVisible = false,
                changePasswordActive = false,
                errorText = null
            )
            loginContext.updatePassword(hashedPassword)
            successCallback()
        }
    }

    fun showMasterPasswordForDeletionVisible(value: Boolean) {
        _profileUIState.value =
            _profileUIState.value.copy(deletionMasterPasswordDialogVisible = value)
    }

    fun cancelUpdatePassword() {
        _profileUIState.value = _profileUIState.value.copy(
            newPasswordText = "",
            oldPasswordText = "",
            newPasswordTextRepeat = "",
            newPasswordVisible = false,
            oldPasswordVisible = false,
            newPasswordRepeatVisible = false,
            changePasswordActive = false,
            errorText = null
        )
    }

    fun clearError() {
        _profileUIState.value = _profileUIState.value.copy(errorText = null)
    }

    fun logOut() {
        loginContext.logOut()
    }

    fun showConfirmDelete(value: Boolean) {
        _profileUIState.value = _profileUIState.value.copy(confirmDeleteVisible = value)
    }

    fun deleteProfile(callback: () -> Unit) {
        viewModelScope.launch {
            credentialsUseCases.getCredentials(loginContext.authenticatedLogin!!.id!!).first()
                .forEach {
                    credentialsUseCases.deleteCredential(it)
                }
            accountsUseCases.deleteAccount(loginContext.authenticatedLogin!!)
            loginContext.logOut()
            callback()
        }
    }

    fun setError(errorText: String) {
        _profileUIState.value = _profileUIState.value.copy(errorText = errorText)
    }
}