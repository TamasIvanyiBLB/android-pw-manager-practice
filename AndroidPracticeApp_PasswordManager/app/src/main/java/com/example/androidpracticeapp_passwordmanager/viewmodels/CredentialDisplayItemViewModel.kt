package com.example.androidpracticeapp_passwordmanager.viewmodels

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CredentialDisplayItemViewModel(credential: CredentialVM) : ViewModel() {
    private var _passwordVisible: MutableState<Boolean> = mutableStateOf(false)
    private val _showDeleteMenu: MutableState<Boolean> = mutableStateOf(false)
    private val _scaleAnimatable = mutableStateOf(Animatable(1f))
    val scaleAnimatable: State<Animatable<Float, AnimationVector1D>> = _scaleAnimatable
    val passwordVisible: State<Boolean> = _passwordVisible
    private var _credential: MutableState<CredentialVM> = mutableStateOf(credential)
    val credential: State<CredentialVM> = _credential
    private val _offsetX = mutableFloatStateOf(0f)
    val offsetX: State<Float> = _offsetX
    private val _cardAnimationOffset = mutableStateOf(Animatable(0f))
    val cardAnimationOffset: State<Animatable<Float, AnimationVector1D>> = _cardAnimationOffset
    val showDeleteMenu: State<Boolean> = _showDeleteMenu

    fun setPasswordVisibility(value: Boolean) {
        _passwordVisible.value = value
    }

    fun setShowDeleteMenu(value: Boolean) {
        _showDeleteMenu.value = value
    }

    fun setOffsetValue(value: Float) {
        _offsetX.floatValue = value
    }

}