package com.inconus.mealmanagement.nav

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
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
    authViewModel: AuthViewModel
) {
    NavHost(
        modifier = Modifier,
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
            CameraAccessRequestScreen(qrViewModel, navController)
        }
        composable(route = "qrScanner",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            })
        {
            QrScanningScreen(qrViewModel)
        }
        composable("calculate",
            enterTransition = {
                EnterTransition.None
            },
            exitTransition = {
                ExitTransition.None
            }
        ) {
            CalculateScreen()
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

