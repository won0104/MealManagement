package com.inconus.mealmanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun BaseScreen(navController: NavHostController, viewModel: AuthViewModel) {
    val loginStatus by viewModel.loginStatus.observeAsState(false)
    MealManagementTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            if (loginStatus) {
                MainScreen(navController)
            } else {
                LoginScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}

