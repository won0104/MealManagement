package com.inconus.mealmanagement.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.util.cameraPermission
import com.inconus.mealmanagement.vm.QrViewModel
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QrScannerScreen(viewModel: QrViewModel) {
    val context = LocalContext.current
    val cameraPermission = cameraPermission(viewModel)
    val duplication = viewModel.duplication.observeAsState()
    val showPermissionDialog = viewModel.showPermissionDialog.observeAsState(false)
    val showErrorDialog = viewModel.showErrorDialog.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")

    var isRefreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
        })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.BottomStart
    ) {
        Box {
            if (cameraPermission) {
                CameraPreview { result ->
                    result.fold(
                        onSuccess = { employee ->
                            viewModel.scanSuccess(employee)
                            Toast.makeText(
                                context,
                                "${employee.name}님, 스캔 되었습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = { exception ->
                            viewModel.scanFailure(exception.message ?: "알 수 없는 Error")
                        }
                    )
                }
            } else {
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("카메라 접근 권한이 필요합니다.")
                    Spacer(modifier = Modifier.heightIn(10.dp))
                    Button(onClick = {
                        navigateToSettings(context); viewModel.updateShowPermissionDialog(
                        false
                    )
                    }) {
                        Text(text = "설정으로 이동")
                    }
                }
            }
        }

        // DB insert에서 확인 하면 중복 체크
//        duplication.value?.let { exists ->
//            if (exists) {
//                Toast.makeText(context, "이미 입력된 값입니다.", Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    ErrorDialog(showErrorDialog as MutableState<Boolean>, errorMessage)

    PermissionDeniedDialog(
        showPermissionDialog as MutableState<Boolean>,
        { viewModel.updateShowPermissionDialog(false) },
        { navigateToSettings(context); viewModel.updateShowPermissionDialog(false) })
}


