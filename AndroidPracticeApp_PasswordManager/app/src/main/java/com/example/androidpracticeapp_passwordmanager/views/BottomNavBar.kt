package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.viewmodels.MainContentViewModel

@Composable
fun BottomNavBar(vm: MainContentViewModel) {
    NavigationBar(modifier = Modifier.fillMaxWidth(), containerColor = colorResource(R.color.bg)) {
        vm.navigationContents.value.forEach { item ->
            NavigationBarItem(
                selected = vm.currentNavigationContent.value == item,
                onClick = { vm.navigateToContent(item.index) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = stringResource(item.labelResourceId)
                    )
                },
                label = { Text(stringResource(item.labelResourceId)) },
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

data class NavItem(val navRoute: String, val icon: ImageVector, val label: String)
