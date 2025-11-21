package com.example.androidpracticeapp_passwordmanager.utils

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.androidpracticeapp_passwordmanager.R

sealed class BottomNavigationContent (val index: Int, val icon: ImageVector, val labelResourceId: Int) {
    data object CredentialListContent : BottomNavigationContent(0, Icons.Filled.FormatListNumbered,
        R.string.credentialList
    )

    data object ProfileContent: BottomNavigationContent(1, Icons.Filled.ManageAccounts, R.string.profile)
}