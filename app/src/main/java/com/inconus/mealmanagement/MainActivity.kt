package com.inconus.mealmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.ui.BaseScreen
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.util.SharedPreferencesTokenProvider
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.QrViewModel
import com.inconus.mealmanagement.vm.TestViewModel


class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val qrViewModel:QrViewModel by viewModels()
    //private val testViewModel: TestViewModel by viewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tokenProvider = SharedPreferencesTokenProvider(this)
        RetrofitClient.setTokenProvider(tokenProvider)

        setContent {
            val navController = rememberNavController()

            MealManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BaseScreen(navController,authViewModel,qrViewModel)
                }
            }
        }
    }
}