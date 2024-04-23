package com.inconus.mealmanagement.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel) {
    var userId by remember { mutableStateOf("01044455107") }
    var userPassword by remember { mutableStateOf("5107") }
    val loginStatus by viewModel.loginStatus.observeAsState("false")
    val errorMessage by viewModel.errorMessage.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
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
                .fillMaxWidth(),
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
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        )
        Spacer(modifier = Modifier.heightIn(20.dp))
        Button(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                viewModel.updateUserId(userId)
                viewModel.updateUserPassword(userPassword)
                viewModel.loginUser()
            }
        ) {
            Text("로그인")
        }

        Spacer(modifier = Modifier.heightIn(20.dp))

        if (loginStatus==true){
            Text(" ${viewModel.name.value} : 로그인 성공")
            Text("${viewModel.token.value}")
        }
        if(errorMessage.isNotEmpty()){
            Text(errorMessage)
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewLoginScreen() {
//    val viewModel = androidViewModel();
//    LoginScreen(viewModel,testViewModel)
//}