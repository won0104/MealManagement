package com.inconus.mealmanagement.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun BaseScreen(navController: NavHostController, viewModel: AuthViewModel){
    Column(modifier= Modifier.fillMaxSize()){
        MainNavHost(navController, viewModel)
        LoginScreen(navController = navController, viewModel = viewModel)
    }
}