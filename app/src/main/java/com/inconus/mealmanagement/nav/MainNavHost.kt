package com.inconus.mealmanagement.nav

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inconus.mealmanagement.ui.CalculateScreen
import com.inconus.mealmanagement.ui.MyPageScreen
import com.inconus.mealmanagement.ui.QrPermissionScreen
import com.inconus.mealmanagement.ui.QrScannerScreen
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    qrViewModel: QrViewModel,
    authViewModel : AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "qrPermission"
    ) {
        composable("qrPermission") {
            QrPermissionScreen(qrViewModel, navController)
        }
        composable("qrScanner") {
            QrScannerScreen(qrViewModel)
        }
        composable("myPage"){
            MyPageScreen(authViewModel)
        }
        composable("calculate"){
            CalculateScreen()
        }
    }
}

@Composable
fun NavController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

