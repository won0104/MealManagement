package com.inconus.mealmanagement.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    data object Login : Screen("login",Icons.Filled.PlayArrow,"Login")
    data object QrScanner : Screen("qrScanner", Icons.Filled.Add, "QR코드")
    data object MyPage : Screen("mypage", Icons.Filled.AccountBox, "내 정보")
    data object Calculate : Screen("calculate", Icons.Filled.DateRange, "정산")
}
