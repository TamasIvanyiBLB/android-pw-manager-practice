package com.example.androidpracticeapp_passwordmanager.database.useCase.account

import com.example.androidpracticeapp_passwordmanager.database.source.AccountDao

class GetHasAccountSetupUseCase(private val accountDao: AccountDao) {
    suspend operator fun invoke(): Boolean {
        return accountDao.getIsAccountSetUp() != 0
    }
}