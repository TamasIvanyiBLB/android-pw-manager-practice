package com.example.androidpracticeapp_passwordmanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidpracticeapp_passwordmanager.database.DatabaseString

@Entity(tableName = DatabaseString.TABLE_NAME_ACCOUNT)
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val login: String,
    val password: String
)