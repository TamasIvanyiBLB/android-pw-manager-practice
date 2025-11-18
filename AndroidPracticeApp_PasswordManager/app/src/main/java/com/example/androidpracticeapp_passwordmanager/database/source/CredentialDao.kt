package com.example.androidpracticeapp_passwordmanager.database.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.androidpracticeapp_passwordmanager.database.DatabaseString
import com.example.androidpracticeapp_passwordmanager.database.model.Credential
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {

    @Query("SELECT * FROM ${DatabaseString.TABLE_NAME_CREDENTIAL}")
    fun getCredentials(): Flow<List<Credential>>

    @Query("SELECT * FROM ${DatabaseString.TABLE_NAME_CREDENTIAL} WHERE ${DatabaseString.COLUMN_ID} = :id")
    suspend fun getCredentialById(id: Int): Credential?

    @Delete
    suspend fun deleteCredential(credential: Credential)

    @Upsert
    suspend fun upsertCredential(credential: Credential)
}