package com.inconus.mealmanagement.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.ui.theme.pretendard
import com.inconus.mealmanagement.vm.AuthViewModel

@Composable
fun MyPageScreen(viewModel: AuthViewModel) {
    val context = LocalContext.current
    var showContractInfo by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        val maxWidth = maxWidth.value
        val padding20 = (maxWidth * 0.05f).dp.coerceAtLeast(10.dp)

        Column(
            modifier = Modifier.fillMaxSize(),
            Arrangement.Top, Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(padding20)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${viewModel.name.value}",
                        style = MaterialTheme.typography.labelSmall
                    ) // 로그인한 식당 이름
                    Button(
                        onClick = { showEditDialog=true },
                        modifier = Modifier.size(60.dp, 24.dp),
                        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFBBBBBB)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.edit),
                            fontSize = 12.sp,
                            fontFamily = pretendard,
                            fontWeight = FontWeight.Medium,
                            color = Color.White
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = padding20),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.contact),
                        Modifier.padding(end = padding20),
                        style = MaterialTheme.typography.bodySmall.copy(colorResource(R.color.gray))
                    )
                    Text(
                        text = "${viewModel.userId.value}",
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(padding20, 10.dp, 0.dp, padding20),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.inquiry),
                        Modifier.padding(end = padding20),
                        style = MaterialTheme.typography.bodySmall.copy(colorResource(R.color.gray))
                    )
                    Text(
                        text = "keepme@gmail.com",
                        style = MaterialTheme.typography.titleSmall.copy(fontSize = 14.sp)
                    )
                }
            }
            Spacer(modifier = Modifier.heightIn(10.dp))
            InfoBox(stringResource(id = R.string.contract_info)) { showContractInfo = true }
            InfoBox(stringResource(id = R.string.version_info) + " 1.1.1") {}

            val logoutSuccessMessage = stringResource(id = R.string.logout_success)
            InfoBox(stringResource(id = R.string.logout)) {
                viewModel.logoutUser()
                Toast.makeText(
                    context,
                    logoutSuccessMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    if (showContractInfo) {
        ContractInfoDialog(showContractInfo = showContractInfo, onClose = { showContractInfo = false })
    }

    if (showEditDialog) {
        ProfileEditDialog(viewModel = viewModel, onDismiss = { showEditDialog = false })
    }
}




@Composable
fun InfoBox(contents: String, onClick: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp, 5.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onClick() },

        )
    {
        Text(
            text = contents,
            modifier = Modifier.padding(20.dp, 15.dp),
            style = MaterialTheme.typography.bodyMedium.copy(colorResource(R.color.gray))
        )
    }
}