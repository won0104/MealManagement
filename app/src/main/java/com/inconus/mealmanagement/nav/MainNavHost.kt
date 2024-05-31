package com.inconus.mealmanagement.nav

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.inconus.mealmanagement.ui.CalculateScreen
import com.inconus.mealmanagement.ui.CameraAccessRequestScreen
import com.inconus.mealmanagement.ui.MyPageScreen
import com.inconus.mealmanagement.ui.QrScanningScreen
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.CalculateViewModel
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    qrViewModel: QrViewModel,
    authViewModel: AuthViewModel,
    calculateViewModel:CalculateViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "qrPermission",
    ) {
        composable(
            route = "qrPermission",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        )
        {
            CameraAccessRequestScreen(qrViewModel) { navController.navigate("qrScanner") }
        }
        composable(route = "qrScanner",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            })
        {
            QrScanningScreen(qrViewModel){navController.navigate("qrPermission") }
        }
        composable("calculate",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            CalculateScreen(calculateViewModel)
            //RecordListScreen()
        }
        composable("myPage",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            })
        {
            MyPageScreen(authViewModel)
        }
    }
}

@Composable
fun NavController.currentRoute(): String? {
    val navBackStackEntry by currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

