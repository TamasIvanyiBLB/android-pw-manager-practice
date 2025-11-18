package com.example.androidpracticeapp_passwordmanager.mapper

import com.example.androidpracticeapp_passwordmanager.database.model.Account
import com.example.androidpracticeapp_passwordmanager.viewmodels.AccountVM

class AccountMapper(
) {
    fun fromEntity(entity: Account): AccountVM {
        return AccountVM(
            id = entity.id!!,
            login = entity.login,
            password = entity.password
        )
    }

    fun toEntity(accountVM: AccountVM): Account {
        val id = if (accountVM.id > 0) accountVM.id else null
        return Account(
            id,
            accountVM.login,
            accountVM.password
        )
    }
}