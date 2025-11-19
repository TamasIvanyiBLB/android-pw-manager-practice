package com.example.androidpracticeapp_passwordmanager.views

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.utils.Screen

@Composable
@Preview
fun BottomNavBar() {
    val items = listOf(
        NavItem(Screen.CredentialsListScreen.route, Icons.Filled.FormatListNumbered, "Credentials"),
        NavItem("Profile", Icons.Filled.ManageAccounts, "Account"),
    )

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = true,
                onClick = {  },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(R.color.primary),
                    selectedTextColor = colorResource(R.color.primary),
                    unselectedIconColor = colorResource(R.color.text),
                    unselectedTextColor = colorResource(R.color.text),
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

data class NavItem(val navRoute:String, val icon: ImageVector, val label: String)
