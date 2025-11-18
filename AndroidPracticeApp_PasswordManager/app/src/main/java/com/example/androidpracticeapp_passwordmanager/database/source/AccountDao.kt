package com.example.androidpracticeapp_passwordmanager.database.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.androidpracticeapp_passwordmanager.database.DatabaseString
import com.example.androidpracticeapp_passwordmanager.database.model.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Query("SELECT * FROM ${DatabaseString.TABLE_NAME_ACCOUNT}")
    fun getAccounts(): Flow<List<Account>>

    @Delete
    suspend fun deleteAccount(account: Account)

    @Insert
    suspend fun createAccount(account: Account)

    @Query("UPDATE ${DatabaseString.TABLE_NAME_ACCOUNT} SET ${DatabaseString.COLUMN_PASSWORD} = :password WHERE ${DatabaseString.COLUMN_ID} = :id")
    suspend fun updateAccountPassword(id: Int, password: String)

    @Query("SELECT EXISTS(SELECT 1 FROM ${DatabaseString.TABLE_NAME_ACCOUNT})")
    suspend fun getIsAccountSetUp(): Int
}