package com.example.androidpracticeapp_passwordmanager.database.useCase.credential

import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import com.example.androidpracticeapp_passwordmanager.database.source.CredentialDao

class GetCredentialByIdUseCase(private val credentialDao: CredentialDao) {
    suspend operator fun invoke(id: Int): Credential? {
        return credentialDao.getCredentialById(id)
    }
}