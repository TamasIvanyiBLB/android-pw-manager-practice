package com.example.androidpracticeapp_passwordmanager.database.useCase.account

import com.example.androidpracticeapp_passwordmanager.database.model.Account
import com.example.androidpracticeapp_passwordmanager.database.source.AccountDao
import kotlinx.coroutines.flow.Flow

class GetAccountsUseCase(private val accountDao: AccountDao) {
    operator fun invoke(): Flow<List<Account>> {
        return accountDao.getAccounts()
    }
}