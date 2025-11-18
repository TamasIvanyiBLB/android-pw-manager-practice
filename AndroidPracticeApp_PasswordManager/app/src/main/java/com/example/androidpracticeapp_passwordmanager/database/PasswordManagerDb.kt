package com.example.androidpracticeapp_passwordmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidpracticeapp_passwordmanager.database.model.Account
import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import com.example.androidpracticeapp_passwordmanager.database.source.AccountDao
import com.example.androidpracticeapp_passwordmanager.database.source.CredentialDao

@Database(entities = [Credential::class, Account::class], version = 2)
abstract class PasswordManagerDb : RoomDatabase() {

    abstract val credentialDao: CredentialDao
    abstract val accountDao: AccountDao

    companion object {
        const val DATABASE_NAME = "passwordmanager.db"
    }
}