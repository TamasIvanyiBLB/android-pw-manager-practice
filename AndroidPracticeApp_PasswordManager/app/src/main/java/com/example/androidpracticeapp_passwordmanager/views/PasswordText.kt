package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.androidpracticeapp_passwordmanager.R

@Composable
fun PasswordText(password: String) {
    val dots = "●".repeat(password.length)
    Text(text = dots, color = colorResource(R.color.text), fontWeight = FontWeight.Medium)
}