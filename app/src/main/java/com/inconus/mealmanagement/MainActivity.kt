package com.inconus.mealmanagement

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.nav.MainNavHost
import com.inconus.mealmanagement.ui.AuthDecisionScreen
import com.inconus.mealmanagement.ui.AuthenticatedMainScreen
import com.inconus.mealmanagement.ui.BottomNavigationBar
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.util.SharedPreferencesTokenProvider
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val qrViewModel:QrViewModel by viewModels()
    //private val testViewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val tokenProvider = SharedPreferencesTokenProvider(this)
//        RetrofitClient.setTokenProvider(tokenProvider)
        //todo viewModel에 정의했으면 그거 쓰는게 더 나아보여요
        authViewModel.initRetrofitClient()

        setContent {
            val navController = rememberNavController()

            MealManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
//                    AuthDecisionScreen(navController,authViewModel,qrViewModel)
                    //todo AuthDecisionScreen에 있던 로직 옮겼습니다. 너무 안쪼개도 돼요
                    val loginStatus by authViewModel.loginStatus.observeAsState(false)
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (loginStatus) {
//                            AuthenticatedMainScreen(navController,qrViewModel,authViewModel)
                            //todo 3줄 짜리라 그냥 메인에 올렸어요
                            Scaffold(
                                bottomBar = { BottomNavigationBar(navController) }
                            ) {
                                MainNavHost(navController, qrViewModel, authViewModel)
                            }
                        } else {
                            //todo navController를 넘기는 것 보다 navigate 이벤트를 넘기는 것이 더 안전(람다로 넘겨도 됨)
                            LoginScreen(navController = navController, viewModel = authViewModel)
                            //AuthNavHost(navController, authViewModel)
                        }
                    }
                }
            }
        }
    }
}