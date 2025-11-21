package com.example.androidpracticeapp_passwordmanager.utils

sealed class Screen(val route: String) {

    data object CreateLoginScreen : Screen(NavRoutes.CREATE_LOGIN_SCREEN)
    data object LoginScreen : Screen(NavRoutes.LOGIN_SCREEN)
    data object MainContentScreen : Screen(NavRoutes.MAIN_CONTENT_SCREEN)
    data object CredentialCreateEditScreen :
        Screen("${NavRoutes.CREDENTIAL_CREATE_EDIT_SCREEN}?${NavArgs.CREDENTIAL_ID}={${NavArgs.CREDENTIAL_ID}}") {

        fun createRoute(credentialId: Int? = null): String {
            return if (credentialId != null) {
                "${NavRoutes.CREDENTIAL_CREATE_EDIT_SCREEN}?${NavArgs.CREDENTIAL_ID}=$credentialId"
            } else {
                NavRoutes.CREDENTIAL_CREATE_EDIT_SCREEN
            }
        }
    }
}