package com.inconus.mealmanagement.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.ui.QrScannerScreen
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun MainNavHost(navController: NavHostController,
                authViewModel: AuthViewModel,){
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login"){
            LoginScreen(navController,authViewModel)
        }
        composable("qrScanner"){
            QrScannerScreen()
        }
    }
}