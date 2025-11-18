package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.androidpracticeapp_passwordmanager.R
import com.example.androidpracticeapp_passwordmanager.utils.Screen
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialDisplayItemViewModel
import com.example.androidpracticeapp_passwordmanager.viewmodels.CredentialVM
import kotlin.math.abs
import kotlin.math.roundToInt


@Composable
fun CredentialDisplayItem(
    vm: CredentialDisplayItemViewModel,
    navController: NavController,
    deleteCredentialCallback: (CredentialVM) -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = colorResource(R.color.text)

    LaunchedEffect(vm.offsetX.value) {
        vm.cardAnimationOffset.value.animateTo(
            targetValue = vm.offsetX.value,
            animationSpec = tween(durationMillis = 150)
        )
    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Box(modifier = Modifier.offset { IntOffset(vm.offsetX.value.roundToInt(), 0) }) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .border(1.dp, colorResource(R.color.primary), RoundedCornerShape(16.dp))
                    .pointerInput(Unit) {
                        detectDragGesturesAfterLongPress(
                            onDragStart = { vm.setShowDeleteMenu(true) },
                            onDragEnd = {
                                if (vm.offsetX.value < -3f) {
                                    vm.setOffsetValue(-200f)
                                } else {
                                    vm.setOffsetValue(0f)
                                    vm.setShowDeleteMenu(false)
                                }
                            },
                            onDragCancel = {
                                vm.setOffsetValue(0f)
                                vm.setShowDeleteMenu(false)
                            },
                            onDrag = { change, dragAmount ->
                                change.consume()
                                val newOffset =
                                    (vm.offsetX.value + dragAmount.x).coerceIn(
                                        -200f,
                                        0f
                                    )
                                vm.setOffsetValue(newOffset)
                            })
                    }

                    .then(modifier),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = colorResource(R.color.bg)
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(10.dp))
                {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            vm.credential.value.title,
                            style = TextStyle(fontSize = 18.sp),
                            color = textColor,
                            fontWeight = FontWeight.SemiBold
                        )
                        IconButton(onClick = {
                            navController.navigate(
                                Screen.CredentialCreateEditScreen.createRoute(
                                    vm.credential.value.id
                                )
                            )
                        }, modifier = Modifier.size(20.dp)) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                tint = textColor,
                                contentDescription = null
                            )
                        }
                    }
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = colorResource(R.color.primary)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 5.dp),
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = null,
                            tint = textColor
                        )
                        Text(
                            vm.credential.value.username ?: "",
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 5.dp),
                            imageVector = Icons.Filled.Email,
                            contentDescription = null,
                            tint = textColor
                        )
                        Text(
                            vm.credential.value.email ?: "",
                            color = textColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    PasswordDisplayContent(vm)
                }
            }
        }
        if (vm.showDeleteMenu.value) {
            ElevatedCard(
                modifier = Modifier
                    .fillMaxHeight()
                    .width((abs(vm.offsetX.value) / 1.5).dp)
                    .align(Alignment.CenterEnd),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = colorResource(R.color.accent)
                ),
                shape = RoundedCornerShape(8.dp),
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    IconButton(
                        onClick = { deleteCredentialCallback(vm.credential.value) },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Icon(
                            Icons.Filled.Delete,
                            tint = colorResource(R.color.fail),
                            contentDescription = null,
                            modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                }
            }
        }
    }
}





