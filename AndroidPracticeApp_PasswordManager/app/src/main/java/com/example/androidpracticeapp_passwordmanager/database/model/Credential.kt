package com.example.androidpracticeapp_passwordmanager.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.androidpracticeapp_passwordmanager.database.DatabaseString

@Entity(
    tableName = DatabaseString.TABLE_NAME_CREDENTIAL,
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = [DatabaseString.COLUMN_ID],
            childColumns = [DatabaseString.COLUMN_ACCOUNT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = [DatabaseString.COLUMN_ACCOUNT_ID])]
)
data class Credential(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val username: String?,
    val email: String?,
    val password: String,
    val accountId: Int
)