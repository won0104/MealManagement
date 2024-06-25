package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.CalculateViewModel
import com.inconus.mealmanagement.vm.QrViewModel

// 로고, 로그인 화면 제외 나머지 화면 (QR 인식, 정산, 마이페이지)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticatedMainScreen(
    qrViewModel: QrViewModel,
    authViewModel: AuthViewModel,
    calculateViewModel: CalculateViewModel
) {
    val localNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(localNavController, qrViewModel) }
    ) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val logoWidth = maxWidth * 0.25f
            val logoPadding = maxWidth * 0.05f
            val navHostPaddingTop = logoWidth*0.5f + logoPadding

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logo_description),
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(logoPadding)
                    .size(logoWidth ,logoWidth*0.35f)
            )
            MainNavHost(
                localNavController,
                qrViewModel,
                authViewModel,
                calculateViewModel,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(top = navHostPaddingTop)
            )
        }
    }
}
