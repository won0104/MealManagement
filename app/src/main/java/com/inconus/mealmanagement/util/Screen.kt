package com.inconus.mealmanagement.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.inconus.mealmanagement.R

sealed class Screen(
    val route: String,
    val iconUnselected: @Composable () -> Painter,
    val iconSelected: @Composable () -> Painter,
    val titleResId: Int
) {
    data object QrPermission : Screen("qrPermission",
        { painterResource(R.drawable.ico_qr_line) },
        { painterResource(R.drawable.ico_qr) },
         R.string.qrPermission)

    data object MyPage : Screen("myPage",
        { painterResource(R.drawable.ico_my_line) },
        { painterResource(id = R.drawable.ico_my) },
        R.string.myPage)

    data object Calculate : Screen("calculate",
        { painterResource(R.drawable.ico_wallet_line) },
        { painterResource(id = R.drawable.ico_wallet) },
        R.string.calculate)
}
