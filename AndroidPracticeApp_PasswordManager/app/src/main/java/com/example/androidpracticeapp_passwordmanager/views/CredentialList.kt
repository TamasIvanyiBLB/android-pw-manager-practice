package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialDisplayItemViewModel
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialListViewModel

@Composable
fun CredentialList(navController: NavController, vm: CredentialListViewModel = hiltViewModel()) {
    Column {
        SearchBar(
            vm.searchText.value,
            callback = { value -> vm.onFilterTextChanged(value) },
            modifier = Modifier.padding(top = 20.dp)
        )
        ElevatedButton(
            onClick = {
                navController.navigate(
                    Screen.CredentialCreateEditScreen.createRoute(
                        null
                    )
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .padding(horizontal = 10.dp),
            colors = ButtonDefaults.elevatedButtonColors(containerColor = colorResource(R.color.button))
        ) {
            Text(
                stringResource(R.string.createNewCredential),
                color = Color.White,
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
        }
        LazyColumn {

            items(vm.credentialsFiltered.value, key = { it.id }) { credential ->
                Column {
                    HorizontalDivider(Modifier, 10.dp, color = Color.Transparent)
                    CredentialDisplayItem(
                        CredentialDisplayItemViewModel(credential),
                        navController,
                        deleteCredentialCallback = { cred -> vm.deleteDropDownItem(cred) }
                    )
                }
            }

        }
    }
}