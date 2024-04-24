package com.inconus.mealmanagement.nav

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inconus.mealmanagement.ui.CalculateScreen
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.ui.MyPageScreen
import com.inconus.mealmanagement.ui.QrScannerScreen
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    //authViewModel: AuthViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = "qrScanner"
    ) {
        composable("qrScanner") {
            QrScannerScreen()
        }
        composable("mypage"){
            MyPageScreen()
        }
        composable("Calculate"){
            CalculateScreen()
        }
    }
}

@Composable
fun NavController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

