package com.inconus.mealmanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.ui.theme.MealManagementTheme
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun LoginScreen(viewModel: AuthViewModel) {
    var userId by remember { mutableStateOf("01044455107") }
    var userPassword by remember { mutableStateOf("5107") }
    val errorMessage by viewModel.errorMessage.observeAsState("")
    val showDialog = remember { mutableStateOf(false) }
    var autoLogin by remember { mutableStateOf(false) } // 자동 로그인 기능 구현 X
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

    // 공통 Modifier
    val commonModifier = Modifier
        .padding(horizontal = 20.dp)
        .fillMaxWidth()
        .height(54.dp)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.LogoDescription)
        )
        Spacer(modifier = Modifier.heightIn(20.dp))
        Text(
            stringResource(R.string.app_title),
            style = MaterialTheme.typography.bodySmall,
        )
        Spacer(modifier = Modifier.heightIn(30.dp))

        TextField(
            value = userId,
            onValueChange = { newText -> userId = newText },
            label = {
                if (userId.isEmpty()) Text(
                    stringResource(R.string.id),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFA8A8A8)
                )
            },
            singleLine = true,
            modifier = commonModifier,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.heightIn(8.dp))
        TextField(
            value = userPassword,
            onValueChange = { newText -> userPassword = newText },
            label = {
                if (userPassword.isEmpty()) Text(
                    stringResource(R.string.pw),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFA8A8A8)
                )
            },
            singleLine = true,
            modifier = commonModifier,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation(), // 비밀번호 가리기
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            )
        )


        Spacer(modifier = Modifier.heightIn(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        ) {
            Checkbox(
                checked = autoLogin,
                onCheckedChange = { autoLogin = it },
                colors = CheckboxDefaults.colors(
                    uncheckedColor = Color(0xFF171C61),
                    checkedColor = MaterialTheme.colorScheme.primary,
                    checkmarkColor = Color.White
                )
            )
            Text(
                text = stringResource(R.string.auto_login),
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        Spacer(modifier = Modifier.heightIn(10.dp))

        Button(
            modifier = commonModifier,
            shape = RoundedCornerShape(12.dp),
            onClick = {
                viewModel.updateUserId(userId)
                viewModel.updateUserPassword(userPassword)
                viewModel.loginUser()
            }
        ) {
            Text(stringResource(R.string.login))
        }
        Spacer(modifier = Modifier.heightIn(20.dp))
    }
}


@Preview
@Composable
fun Preview() {
    MealManagementTheme() {
        val viewModel = AuthViewModel()
        LoginScreen(viewModel)
    }
}
