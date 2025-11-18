package com.example.androidpracticeapp_passwordmanager.database.useCase.credential

data class CredentialsUseCases(
    val getCredentials: GetCredentialsUseCase,
    val getCredentialById: GetCredentialByIdUseCase,
    val upsertCredential: UpsertCredentialUseCase,
    val deleteCredential: DeleteCredentialUseCase,
)