package com.inconus.mealmanagement.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
    var autoLogin by remember { mutableStateOf(false) } // 기능 구현 X
    val focusManager = LocalFocusManager.current

    //Error 발생시 다이얼로그 표시
    LaunchedEffect(errorMessage) {
        showDialog.value = errorMessage.isNotEmpty()
    }
    if (showDialog.value) {
        ErrorDialog(showDialog.value, errorMessage, onDismiss = {
            showDialog.value = false
            viewModel.updateErrorMessage("")
        })
    }

    BoxWithConstraints {
        val maxWidth = maxWidth
        val commonModifier = Modifier
            .padding(horizontal = maxWidth * 0.05f)
            .fillMaxWidth()
            .height(maxWidth * 0.15f)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    focusManager.clearFocus()
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logoDescription)
            )
            Spacer(modifier = Modifier.height(maxWidth * 0.05f))
            Text(
                stringResource(R.string.app_title),
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(maxWidth * 0.08f))


            BasicTextField(
                value = userId,
                onValueChange = { userId = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = if (userId.isEmpty()) Color.Gray else Color.Black
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                modifier = commonModifier
                    .background(
                        Color.White,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (userId.isEmpty()) {
                            Text(
                                stringResource(id = R.string.id),
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(
                                        0xFFA8A8A8
                                    )
                                ),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                        innerTextField()
                    }
                }
            )


            Spacer(modifier = Modifier.height(maxWidth * 0.02f))

            BasicTextField(
                value = userPassword,
                onValueChange = { userPassword = it },
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = if (userPassword.isEmpty()) Color.Gray else Color.Black  // 입력에 따라 색상 변경
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                visualTransformation = PasswordVisualTransformation(),  // 비밀번호 가리기
                modifier = commonModifier
                    .background(
                        Color.White,
                        RoundedCornerShape(10.dp)
                    )
                    .padding(vertical = 16.dp, horizontal = 20.dp)
                    .fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box(contentAlignment = Alignment.CenterStart) {
                        if (userPassword.isEmpty()) {
                            Text(
                                stringResource(id = R.string.pw), // 라벨 텍스트
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFA8A8A8)
                                ),
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                        innerTextField()
                    }
                }
            )


            Spacer(modifier = Modifier.height(maxWidth * 0.03f))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = maxWidth * 0.05f)

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

            Spacer(modifier = Modifier.height(maxWidth * 0.03f))

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
            Spacer(modifier = Modifier.height(maxWidth * 0.05f))
        }
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
