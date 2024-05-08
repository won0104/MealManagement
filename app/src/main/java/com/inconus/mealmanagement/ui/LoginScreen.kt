package com.inconus.mealmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel) {
    MealManagementTheme {
        var userId by remember { mutableStateOf("01044455107") }
        var userPassword by remember { mutableStateOf("5107") }
        val loginStatus by viewModel.loginStatus.observeAsState(false)
        val errorMessage by viewModel.errorMessage.observeAsState("")
        val showDialog = remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current

        //Error 발생시 다이얼로그 표시
        LaunchedEffect(errorMessage) {
            showDialog.value = errorMessage.isNotEmpty()
        }

        if (showDialog.value) {
            ErrorDialog(showDialog.value, errorMessage,
                onDismiss = {
                    showDialog.value = false
                    viewModel.updateErrorMessage("")
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) { focusManager.clearFocus() }
                .background(Color.Transparent)
                .padding(50.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = userId,
                onValueChange = { newText -> userId = newText },
                label = { Text("id") },
                singleLine = true,
                modifier = Modifier
                    .height(65.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(8.dp),
            )
            Spacer(modifier = Modifier.heightIn(10.dp))
            OutlinedTextField(
                value = userPassword,
                onValueChange = { newText -> userPassword = newText },
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .height(65.dp)
                    .width(280.dp),
                shape = RoundedCornerShape(8.dp),
            )
            Spacer(modifier = Modifier.heightIn(20.dp))
            Button(modifier = Modifier
                .height(55.dp)
                .width(190.dp),
                onClick = {
                    viewModel.updateUserId(userId)
                    viewModel.updateUserPassword(userPassword)
                    viewModel.loginUser()
                }
            ) {
                Text("로그인")
            }

            Spacer(modifier = Modifier.heightIn(20.dp))

            // 로그인 성공 시 페이지 이동
            if (loginStatus) {
                navController.navigate("qrScanner")
            }
        }
    }
}

@Composable
fun preview(viewModel: AuthViewModel){
    val localContext = LocalContext.current
    val viewmodel = viewModel<AuthViewModel>()
    LoginScreen(navController = NavHostController(localContext), viewModel = viewmodel)
}