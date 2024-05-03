package com.inconus.mealmanagement.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel

//@Composable
//fun AuthNavHost(navController: NavHostController, authViewModel: AuthViewModel){
//    NavHost(
//        navController = navController,
//        startDestination = "login"
//    ) {
//        composable ("login"){
//            LoginScreen(navController = navController, viewModel = authViewModel)
//        }
//    }
//}