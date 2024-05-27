package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.CalculateViewModel
import com.inconus.mealmanagement.vm.QrViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AuthenticatedMainScreen(qrViewModel: QrViewModel, authViewModel: AuthViewModel,calculateViewModel: CalculateViewModel) {
    val localNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(localNavController,qrViewModel) }
    ) {
        MainNavHost(localNavController, qrViewModel, authViewModel,calculateViewModel)
    }
}
