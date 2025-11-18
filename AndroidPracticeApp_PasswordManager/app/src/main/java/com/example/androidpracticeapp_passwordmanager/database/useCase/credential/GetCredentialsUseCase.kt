package com.example.androidpracticeapp_passwordmanager.database.useCase.credential

import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import com.example.androidpracticeapp_passwordmanager.database.source.CredentialDao
import kotlinx.coroutines.flow.Flow

class GetCredentialsUseCase(private val credentialDao: CredentialDao) {
    operator fun invoke(): Flow<List<Credential>> {
        return credentialDao.getCredentials()
    }
}