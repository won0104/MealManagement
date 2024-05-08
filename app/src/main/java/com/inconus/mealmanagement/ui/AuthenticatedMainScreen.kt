package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticatedMainScreen(navController: NavHostController, qrViewModel: QrViewModel, authViewModel: AuthViewModel) {
    //todo 아래 두 네비는 호이스팅 한 navController를 넘겨준거라 괜찮지만 MainActivity로 올렸어요
    Scaffold(
        bottomBar = { BottomNavigationBar(navController,qrViewModel) }
    ) {
        MainNavHost(navController, qrViewModel, authViewModel)
    }
}
