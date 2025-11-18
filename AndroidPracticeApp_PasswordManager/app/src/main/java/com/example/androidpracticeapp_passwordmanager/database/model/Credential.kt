package com.example.androidpracticeapp_passwordmanager.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidpracticeapp_passwordmanager.database.DatabaseString

@Entity(tableName = DatabaseString.TABLE_NAME_CREDENTIAL)
data class Credential(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val username: String?,
    val email: String?,
    val password: String
)