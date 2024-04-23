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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController, viewModel: AuthViewModel) {
    var userId by remember { mutableStateOf("01044455107") }
    var userPassword by remember { mutableStateOf("5107") }
    val loginStatus by viewModel.loginStatus.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val showDialog = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    LaunchedEffect(errorMessage) {
        if (errorMessage.isNotEmpty()) {
            showDialog.value = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() }  // 클릭 시 키보드 숨기기
            .background(Color.Transparent)
            .padding(50.dp)
        , // 배경색을 투명하게 설정하여 클릭 가능하도록 함
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = userId,
            onValueChange =  { newText -> userId = newText },
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

        if (showDialog.value) {
            ErrorDialog(showDialog, errorMessage)
        }
        if(loginStatus){
            navController.navigate("qrScanner")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    val viewModel = androidViewModel();
//    LoginScreen(viewModel,testViewModel)
//}