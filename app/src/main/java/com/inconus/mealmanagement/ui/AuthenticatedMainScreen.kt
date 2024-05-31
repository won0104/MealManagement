package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.CalculateViewModel
import com.inconus.mealmanagement.vm.QrViewModel

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
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.logoDescription),
                modifier = Modifier
                    .align(Alignment.TopStart)  // 이미지를 오른쪽 상단에 배치
                    .padding(20.dp)
                    .size(100.dp,30.dp)
            )
            MainNavHost(
                localNavController,
                qrViewModel,
                authViewModel,
                calculateViewModel,
                modifier = Modifier
                    .align(Alignment.BottomStart)  // MainNavHost를 상단 겹치지 않게 배치
                    .padding(top = 70.dp)  // 이미지와 겹치지 않도록 여유 공간 추가
            )
        }
    }
}
