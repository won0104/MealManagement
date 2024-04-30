package com.inconus.mealmanagement.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.inconus.mealmanagement.R

sealed class Screen(val route: String, val icon: @Composable () -> Painter, val title: String) {
    data object Login : Screen("login", { painterResource(R.drawable.baseline_login_24) },"Login")
    data object QrPermission : Screen("qrPermission",{ painterResource(R.drawable.baseline_qr_code_scanner_24) }, "QR코드")
    data object MyPage : Screen("myPage", { painterResource(R.drawable.baseline_account_circle_24) }, "내 정보")
    data object Calculate : Screen("calculate", { painterResource(R.drawable.baseline_calculate_24) }, "정산")
}
