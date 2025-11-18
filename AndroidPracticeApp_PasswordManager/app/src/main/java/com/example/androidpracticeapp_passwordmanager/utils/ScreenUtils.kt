package com.example.androidpracticeapp_passwordmanager.utils

object NavRoutes {
    const val CREATE_LOGIN_SCREEN = "create_login_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val CREDENTIAL_LIST_SCREEN = "credentials_list_screen"
    const val CREDENTIAL_CREATE_EDIT_SCREEN = "credential_create_edit_screen"
}

object NavArgs {
    const val CREDENTIAL_ID = "credentialId"
}

sealed class Screen(val route: String) {

    data object CreateLoginScreen : Screen(NavRoutes.CREATE_LOGIN_SCREEN)
    data object LoginScreen : Screen(NavRoutes.LOGIN_SCREEN)
    data object CredentialsListScreen : Screen(NavRoutes.CREDENTIAL_LIST_SCREEN)
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