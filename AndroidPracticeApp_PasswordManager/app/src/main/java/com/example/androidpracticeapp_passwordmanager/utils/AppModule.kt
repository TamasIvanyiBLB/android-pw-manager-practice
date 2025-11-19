package com.example.androidpracticeapp_passwordmanager.utils

import android.app.Application
import androidx.room.Room
import com.example.androidpracticeapp_passwordmanager.database.PasswordManagerDb
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.AccountsUseCases
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.CreateAccountUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.DeleteAccountUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.GetAccountsUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.GetHasAccountSetupUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.account.UpdateAccountPasswordUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.CredentialsUseCases
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.DeleteCredentialUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.GetCredentialByIdUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.GetCredentialsUseCase
import com.example.androidpracticeapp_passwordmanager.database.useCase.credential.UpsertCredentialUseCase
import com.example.androidpracticeapp_passwordmanager.mapper.AccountMapper
import com.example.androidpracticeapp_passwordmanager.mapper.CredentialMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePasswordManagerDatabase(context: Application): PasswordManagerDb {
        return Room.databaseBuilder(
            context,
            PasswordManagerDb::class.java,
            PasswordManagerDb.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCredentialsUseCases(db: PasswordManagerDb): CredentialsUseCases {
        return CredentialsUseCases(
            getCredentials = GetCredentialsUseCase(db.credentialDao),
            getCredentialById = GetCredentialByIdUseCase(db.credentialDao),
            upsertCredential = UpsertCredentialUseCase(db.credentialDao),
            deleteCredential = DeleteCredentialUseCase(db.credentialDao),
        )
    }

    @Provides
    @Singleton
    fun provideAccountsUseCases(db: PasswordManagerDb): AccountsUseCases {
        return AccountsUseCases(
            getAccounts = GetAccountsUseCase(db.accountDao),
            deleteAccount = DeleteAccountUseCase(db.accountDao),
            updateAccountPassword = UpdateAccountPasswordUseCase(db.accountDao),
            createAccount = CreateAccountUseCase(db.accountDao),
            getHasAccountSetUp = GetHasAccountSetupUseCase(db.accountDao)
        )
    }

    @Provides
    @Singleton
    fun provideKeyGen(): KeyGen {
        return KeyGen()
    }

    @Provides
    @Singleton
    fun providePasswordUtils(keyGen: KeyGen): PasswordUtils {
        return PasswordUtils(keyGen)
    }

    @Provides
    @Singleton
    fun provideCredentialMapper(passwordUtils: PasswordUtils): CredentialMapper {
        return CredentialMapper(passwordUtils)
    }

    @Provides
    @Singleton
    fun provideAccountMapper(): AccountMapper {
        return AccountMapper()
    }

    @Provides
    @Singleton
    fun provideLoginContext(passwordUtils: PasswordUtils): LoginContext {
        return LoginContext(passwordUtils)
    }
}