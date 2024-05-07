package com.inconus.mealmanagement.nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inconus.mealmanagement.ui.CalculateScreen
import com.inconus.mealmanagement.ui.MyPageScreen
import com.inconus.mealmanagement.ui.CameraAccessRequestScreen
import com.inconus.mealmanagement.ui.QrScanningScreen
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
            CameraAccessRequestScreen(qrViewModel, navController)
        }
        composable("qrScanner") {
            QrScanningScreen(qrViewModel)
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

