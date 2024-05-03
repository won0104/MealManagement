package com.inconus.mealmanagement.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun MyPageScreen(viewModel :AuthViewModel) {
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        Arrangement.Center, Alignment.CenterHorizontally
    ) {
        Text(text = "마이페이지 화면")
        Button(onClick = {
            viewModel.logoutUser()
            Toast.makeText(context,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show()
        }) {
            Text(text = "Logout")
        }
    }
}