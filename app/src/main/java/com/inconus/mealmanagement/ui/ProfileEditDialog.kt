package com.inconus.mealmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun ProfileEditDialog(viewModel: AuthViewModel, onDismiss: () -> Unit) {
    var newName by remember { mutableStateOf(viewModel.name.value ?: "") }
    var newPhone by remember { mutableStateOf(viewModel.userId.value ?: "") }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "정보 수정",
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(30.dp))

                TextField(
                    value = newName,
                    onValueChange = { newName = it },
                    singleLine = true,
                    label = { Text(stringResource(id = R.string.update_name)) },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background,
                            RoundedCornerShape(10.dp)
                        )
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.primary,
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = newPhone,
                    onValueChange = { newPhone = it },
                    singleLine = true,
                    label = { Text(stringResource(id = R.string.update_phone)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        cursorColor = MaterialTheme.colorScheme.primary,
                    )
                )
            }
            Row(Modifier.height(50.dp)) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    shape = RoundedCornerShape(bottomStart = 10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E8E8))
                ) {
                    Text("취소", style = MaterialTheme.typography.labelMedium, color = Color.Black)
                }
                Button(
                    onClick = {
                        viewModel.updateUserInfo(newName,newPhone)
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.5f),
                    shape = RoundedCornerShape(bottomEnd = 10.dp)
                ) {
                    Text("저장", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}
