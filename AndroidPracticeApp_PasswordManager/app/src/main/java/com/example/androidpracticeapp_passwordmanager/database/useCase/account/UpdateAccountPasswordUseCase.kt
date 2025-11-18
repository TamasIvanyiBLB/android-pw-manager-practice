package com.example.androidpracticeapp_passwordmanager.database.useCase.account

import com.example.androidpracticeapp_passwordmanager.database.source.AccountDao

class UpdateAccountPasswordUseCase(private val accountDao: AccountDao) {
    suspend operator fun invoke(id: Int, password: String) {
        accountDao.updateAccountPassword(id, password)
    }
}