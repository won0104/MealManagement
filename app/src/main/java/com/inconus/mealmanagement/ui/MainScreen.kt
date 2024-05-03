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
fun MainScreen(navController: NavHostController,qrViewModel: QrViewModel,authViewModel: AuthViewModel) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        MainNavHost(navController, qrViewModel, authViewModel)
    }
}
