package com.inconus.mealmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.ui.AuthenticatedMainScreen
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val qrViewModel: QrViewModel by viewModels()
    //private val testViewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authViewModel.initRetrofitClient()

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            MealManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val loginStatus by authViewModel.loginStatus.observeAsState(false)
                    Column(modifier = Modifier.fillMaxSize()) {
                        if (loginStatus) {
                            Toast.makeText(context,"로그인 되었습니다.",Toast.LENGTH_SHORT).show()
                            
                            AuthenticatedMainScreen(navController, qrViewModel, authViewModel)
                        } else {
                            LoginScreen(
                                viewModel = authViewModel,
                                longinSuccess = {
                                    navController.navigate("qrScanner")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}