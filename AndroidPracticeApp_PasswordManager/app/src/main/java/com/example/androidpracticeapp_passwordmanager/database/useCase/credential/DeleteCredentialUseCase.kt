package com.example.androidpracticeapp_passwordmanager.database.useCase.credential

import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import com.example.androidpracticeapp_passwordmanager.database.source.CredentialDao

class DeleteCredentialUseCase(private val credentialDao: CredentialDao) {
    suspend operator fun invoke(credential: Credential) {
        credentialDao.deleteCredential(credential)
    }
}