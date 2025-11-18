package com.example.androidpracticeapp_passwordmanager.database.useCase.account

import com.example.androidpracticeapp_passwordmanager.database.model.Account
import com.example.androidpracticeapp_passwordmanager.database.source.AccountDao

class CreateAccountUseCase(private val accountDao: AccountDao) {
    suspend operator fun invoke(account: Account) {
        accountDao.createAccount(account)
    }
}