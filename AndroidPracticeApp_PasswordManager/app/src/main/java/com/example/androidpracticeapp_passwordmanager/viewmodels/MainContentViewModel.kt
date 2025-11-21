package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.androidpracticeapp_passwordmanager.utils.BottomNavigationContent
import com.example.androidpracticeapp_passwordmanager.utils.LoginContext
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainContentViewModel @Inject constructor(
    private val loginContext: LoginContext
) : ViewModel() {
    private val _navigationContents = mutableStateOf(
        listOf(
            BottomNavigationContent.CredentialListContent,
            BottomNavigationContent.ProfileContent
        )
    )
    val navigationContents: State<List<BottomNavigationContent>> = _navigationContents
    private val _currentNavigationContent = mutableStateOf(_navigationContents.value.first())
    val currentNavigationContent: State<BottomNavigationContent> = _currentNavigationContent

    fun navigateToContent(index: Int) {
        _currentNavigationContent.value = _navigationContents.value.first { it.index == index }
    }
}