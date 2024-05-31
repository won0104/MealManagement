package com.inconus.mealmanagement

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.model.AppDatabase
import com.inconus.mealmanagement.model.EmployeeRepository
import com.inconus.mealmanagement.model.SummaryRepository
import com.inconus.mealmanagement.ui.AuthenticatedMainScreen
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.ui.LoginScreen
import com.inconus.mealmanagement.vm.AuthViewModel
import com.inconus.mealmanagement.vm.CalculateViewModel
import com.inconus.mealmanagement.vm.CalculateViewModelFactory
import com.inconus.mealmanagement.vm.QrViewModel
import com.inconus.mealmanagement.vm.QrViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getDatabase(application)
        val employeeRepository = EmployeeRepository(db.employeeDao())
        val summaryRepository = SummaryRepository(db.summaryDao())

        val qrViewModelFactory = QrViewModelFactory(employeeRepository)
        val calculateViewModelFactory =
            CalculateViewModelFactory(employeeRepository, summaryRepository)
        val authViewModel: AuthViewModel by viewModels()
        val qrViewModel: QrViewModel by viewModels { qrViewModelFactory }
        val calculateViewModel: CalculateViewModel by viewModels { calculateViewModelFactory }

        authViewModel.initRetrofitClient()

        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            MealManagementTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val loginStatus by authViewModel.loginStatus.observeAsState()

                    // Login 화면 , 인증후 나오는 화면
                    NavHost(
                        navController = navController,
                        startDestination = if (loginStatus == true) "authenticatedMainScreen" else "loginScreen"
                    ) {
                        composable("loginScreen") {
                            LoginScreen(viewModel = authViewModel)
                        }
                        composable("authenticatedMainScreen") {
                            AuthenticatedMainScreen(qrViewModel, authViewModel, calculateViewModel)
                        }
                    }

                    // 로그인이 성공적으로 실행 되었을 때
                    authViewModel.loginSuccessEvent.observeAsState().value?.getContentIfNotHandled()
                        ?.let { success ->
                            if (success) {
                                Toast.makeText(
                                    context,
                                    stringResource(id = R.string.login_success),
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.navigate("authenticatedMainScreen") {
                                    popUpTo("loginScreen") { inclusive = true } // 로그인 화면을 스택에서 제거
                                }
                            }
                        }
                }
            }
        }
    }
}
