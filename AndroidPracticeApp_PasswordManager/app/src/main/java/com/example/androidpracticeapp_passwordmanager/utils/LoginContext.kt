package com.example.androidpracticeapp_passwordmanager.utils

import com.example.androidpracticeapp_passwordmanager.database.model.Account

class LoginContext(val passwordUtils: PasswordUtils) {
    private var _authenticatedLogin: Account? = null
    val authenticatedLogin get() = _authenticatedLogin

    fun tryAuthenticate(account: Account, password: String): Boolean {
        if (passwordUtils.comparePassword(account.password, password)) {
            _authenticatedLogin = account
            return true
        }

        return false
    }

    fun updatePassword(hash: String) {
        if (_authenticatedLogin == null) {
            return
        }
        _authenticatedLogin = _authenticatedLogin!!.copy(password = hash)
    }

    fun logOut() {
        _authenticatedLogin = null
    }
}