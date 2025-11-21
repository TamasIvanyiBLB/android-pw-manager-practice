package com.example.androidpracticeapp_passwordmanager.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.CredentialsUseCases
import com.example.androidpracticeapp_passwordmanager.mapper.CredentialMapper
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CredentialListViewModel @Inject constructor(
    private val credentialsUseCases: CredentialsUseCases,
    private val credentialMapper: CredentialMapper,
    private val loginContext: LoginContext
) : ViewModel() {
    private var _credentials: List<CredentialVM> = emptyList()
    private val _credentialsFiltered: MutableState<List<CredentialVM>> = mutableStateOf(emptyList())
    val credentialsFiltered: State<List<CredentialVM>> = _credentialsFiltered

    private val _credentialToDelete: MutableState<CredentialVM?> = mutableStateOf(null)
    val credentialToDelete: State<CredentialVM?> = _credentialToDelete

    private val _searchText: MutableState<String> = mutableStateOf("")
    val searchText: State<String> = _searchText

    init {
        if (loginContext.authenticatedLogin != null) {
            loadCredentials()
        }
        Log.d("app", loginContext.authenticatedLogin.toString())
    }

    fun onFilterTextChanged(value: String) {
        _searchText.value = value
        filterCredentials()
    }


    fun deleteDropDownItem(credential: CredentialVM) {
        viewModelScope.launch {
            credentialsUseCases.deleteCredential(
                credentialMapper.toEntity(credential)
            )
            _credentials = _credentials.filter { it.id != credential.id }
            setUpItemForDeletion()
            filterCredentials()
        }
    }

    fun setUpItemForDeletion(credential: CredentialVM? = null) {
        _credentialToDelete.value = credential
    }

    private fun loadCredentials() {
        credentialsUseCases.getCredentials(loginContext.authenticatedLogin!!.id!!)
            .onEach { credentials ->
                _credentials = credentials.map { credentialMapper.fromEntity(it) }
                filterCredentials()
            }.launchIn(viewModelScope)
    }

    private fun filterCredentials() {
        _credentialsFiltered.value = emptyList()
        if (_searchText.value.isNotEmpty()) {
            _credentialsFiltered.value =
                _credentials.filter { it.title.contains(_searchText.value) }
        } else {
            _credentialsFiltered.value = _credentials
        }
    }
}