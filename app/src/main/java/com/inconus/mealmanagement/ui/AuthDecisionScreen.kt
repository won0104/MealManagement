package com.inconus.mealmanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel


@Composable
fun AuthDecisionScreen(navController: NavHostController, authViewModel: AuthViewModel, qrViewModel: QrViewModel) {
    val loginStatus by authViewModel.loginStatus.observeAsState(false)
    MealManagementTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            if (loginStatus) {
                AuthenticatedMainScreen(navController,qrViewModel,authViewModel)
            } else {
                LoginScreen(navController = navController, viewModel = authViewModel)
                //AuthNavHost(navController, authViewModel)
            }
        }
    }
}

