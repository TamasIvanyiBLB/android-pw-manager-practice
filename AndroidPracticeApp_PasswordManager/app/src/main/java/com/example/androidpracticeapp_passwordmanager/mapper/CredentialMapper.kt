package com.example.androidpracticeapp_passwordmanager.mapper

import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import com.example.androidpracticeapp_passwordmanager.utils.PasswordUtils
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialVM

class CredentialMapper(
    val passwordUtils: PasswordUtils,
) {
    fun fromEntity(entity: Credential): CredentialVM {
        val decryptedPassword = passwordUtils.decryptPassword(entity.password)
        return CredentialVM(
            id = entity.id!!,
            title = entity.title,
            username = entity.username,
            email = entity.email,
            password = decryptedPassword,
            accountId = entity.accountId
        )
    }

    fun toEntity(credentialVM: CredentialVM): Credential {
        val id = if (credentialVM.id > 0) credentialVM.id else null
        val encryptedPassword = passwordUtils.encryptPassword(credentialVM.password)
        return Credential(
            id,
            credentialVM.title,
            credentialVM.username,
            credentialVM.email,
            encryptedPassword,
            credentialVM.accountId
        )
    }
}