package com.example.androidpracticeapp_passwordmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.androidpracticeapp_passwordmanager.ui.theme.AndroidPracticeApp_PasswordManagerTheme
import com.example.androidpracticeapp_passwordmanager.utils.NavArgs
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.MainViewModel
import com.example.androidpracticeapp_passwordmanager.views.CreateLogin
import com.example.androidpracticeapp_passwordmanager.views.CredentialCreateEdit
import com.example.androidpracticeapp_passwordmanager.views.CredentialList
import com.example.androidpracticeapp_passwordmanager.views.Login
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidPracticeApp_PasswordManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val navController = rememberNavController()
                    val vm = hiltViewModel<MainViewModel>()
                    NavHost(
                        navController = navController,
                        startDestination = if (vm.hasLoginSetup.value) Screen.LoginScreen.route else Screen.CreateLoginScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Screen.CreateLoginScreen.route)
                        {
                            CreateLogin(navController)
                        }
                        composable(route = Screen.LoginScreen.route)
                        {
                            Login(navController)
                        }
                        composable(route = Screen.CredentialsListScreen.route) {

                            CredentialList(navController)
                        }
                        composable(
                            route = Screen.CredentialCreateEditScreen.route,
                            arguments = listOf(
                                navArgument(NavArgs.CREDENTIAL_ID) {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )) { backstackEntry ->
                            CredentialCreateEdit(navController)
                        }
                    }

                }
            }
        }
    }
}