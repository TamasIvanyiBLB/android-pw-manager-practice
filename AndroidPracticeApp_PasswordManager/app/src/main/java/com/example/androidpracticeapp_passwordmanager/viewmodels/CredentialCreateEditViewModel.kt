package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.CredentialsUseCases
import com.example.androidpracticeapp_passwordmanager.mapper.CredentialMapper
import com.example.androidpracticeapp_passwordmanager.utils.NavArgs
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CredentialCreateEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val passwordUtils: PasswordUtils,
    private val credentialsUseCases: CredentialsUseCases,
    private val credentialMapper: CredentialMapper
) : ViewModel() {
    private var _passwordLength: Int = 0
    private val _formUIState = mutableStateOf(CredentialUIStateVM())
    val formUIState: State<CredentialUIStateVM> = _formUIState
    private val _credential: MutableState<CredentialVM> = mutableStateOf(createNewCredential())

    init {
        val credentialId = savedStateHandle.get<String>(NavArgs.CREDENTIAL_ID)?.toIntOrNull() ?: -1
        loadCredential(credentialId)
    }


    fun updateTitle(value: String) {
        _formUIState.value = _formUIState.value.copy(title = value)
    }

    fun updateUsername(value: String) {
        _formUIState.value = _formUIState.value.copy(username = value.ifEmpty { null })
    }

    fun updateEmail(value: String) {
        _formUIState.value = _formUIState.value.copy(email = value.ifEmpty { null })
    }

    fun updatePassword(value: String) {
        _formUIState.value = _formUIState.value.copy(password = value)
    }

    fun updatePasswordLength(value: String) {
        val filtered = value.filter { it.isDigit() }
        _passwordLength = filtered.toIntOrNull() ?: 0
        _formUIState.value = _formUIState.value.copy(passwordLengthStr = filtered)
    }

    fun onUseSpecialCharacterChange(value: Boolean) {
        _formUIState.value = _formUIState.value.copy(useSpecialCharacters = value)
    }

    fun onGeneratePassword() {
        _formUIState.value = _formUIState.value.copy(
            password = passwordUtils.generatePassword(
                _passwordLength,
                _formUIState.value.useSpecialCharacters
            )
        )
    }

    fun onSave() {
        val updated = _credential.value.copy(
            id = _credential.value.id,
            title = _formUIState.value.title,
            username = _formUIState.value.username,
            email = _formUIState.value.email,
            password = _formUIState.value.password
        )
        _credential.value = updated

        viewModelScope.launch {
            credentialsUseCases.upsertCredential(credentialMapper.toEntity(_credential.value))
        }

    }

    private fun loadCredential(credentialId: Int) {
        viewModelScope.launch {
            val result = credentialsUseCases.getCredentialById(credentialId)
            if (result != null) {
                _credential.value = credentialMapper.fromEntity(result)
            }
            initFormUIState()
        }
    }

    private fun initFormUIState() {
        _formUIState.value = _formUIState.value.copy(
            title = _credential.value.title,
            username = _credential.value.username,
            email = _credential.value.email,
            password = _credential.value.password,
        )
    }

    private fun createNewCredential(): CredentialVM {
        return CredentialVM(-1, "", null, null, "")
    }
}