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
    //todo MealManagementTheme를 왜 2번 썼는지 모르겠어요, 그냥 setContent로 올려도 문제 없어 보여서 올렸어요
    MealManagementTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            if (loginStatus) {
                AuthenticatedMainScreen(navController,qrViewModel,authViewModel)
            } else {
                //todo navController를 넘기는 것 보다 navigate 이벤트를 넘기는 것이 더 안전(람다로 넘겨도 됨, 구글에서 권장 안해요
                LoginScreen(navController = navController, viewModel = authViewModel)
                //AuthNavHost(navController, authViewModel)
            }
        }
    }
}

