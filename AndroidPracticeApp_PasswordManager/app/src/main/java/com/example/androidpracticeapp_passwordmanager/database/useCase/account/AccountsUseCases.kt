package com.example.androidpracticeapp_passwordmanager.database.useCase.account

data class AccountsUseCases(
    val getAccounts: GetAccountsUseCase,
    val createAccount: CreateAccountUseCase,
    val deleteAccount: DeleteAccountUseCase,
    val updateAccountPassword: UpdateAccountPasswordUseCase,
    val getHasAccountSetUp: GetHasAccountSetupUseCase
)

