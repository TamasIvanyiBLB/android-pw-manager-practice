package com.example.androidpracticeapp_passwordmanager.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

class AutoLogoutObserver(
    private val onLogout: () -> Unit
) : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_STOP) {
            onLogout()
        }
    }
}