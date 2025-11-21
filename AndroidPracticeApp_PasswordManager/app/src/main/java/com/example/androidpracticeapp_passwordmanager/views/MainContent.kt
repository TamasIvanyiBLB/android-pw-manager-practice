package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidpracticeapp_passwordmanager.utils.BottomNavigationContent
import com.example.androidpracticeapp_passwordmanager.viewmodels.MainContentViewModel

@Composable
fun MainContent(navController: NavController, vm: MainContentViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            if (vm.currentNavigationContent.value == BottomNavigationContent.CredentialListContent) {
                CredentialList(navController)
            }
            if (vm.currentNavigationContent.value == BottomNavigationContent.ProfileContent) {
                Profile(navController)
            }
        }
        BottomNavBar(vm)
    }

}