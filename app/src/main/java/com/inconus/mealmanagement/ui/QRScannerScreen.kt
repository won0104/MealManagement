package com.inconus.mealmanagement.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.model.Employee
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun QrScannerScreen(viewModel: QrViewModel,cameraPermission:Boolean) {
    val context = LocalContext.current
    val duplication = viewModel.duplication.observeAsState()
    val showPermissionDialog = viewModel.showPermissionDialog.observeAsState(false)
    val showErrorDialog = viewModel.showErrorDialog.observeAsState(false)
    val errorMessage by viewModel.errorMessage.observeAsState("")

//    LaunchedEffect(errorMessage) {
//        if (errorMessage.isNotEmpty()) {
//            viewModel.updateShowErrorDialog(true)
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomStart
    ) {
        Box{
            if (cameraPermission) {
                CameraPreview { result ->
                    result.fold(
                        onSuccess = { employee ->
                            viewModel.scanSuccess(employee)
                            Toast.makeText(context, "${employee.name}님, 스캔 되었습니다.", Toast.LENGTH_SHORT).show()
                        },
                        onFailure = { exception ->
                            viewModel.scanFailure(exception.message ?: "알 수 없는 Error")
                        }
                    )
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("카메라 접근 권한이 필요합니다.")
                }
            }
        }
        Button(onClick = { /*TODO*/ }) {
            Icon(painterResource(id = R.drawable.baseline_change_circle_24), contentDescription = "회전")
        }
        duplication.value?.let { exists ->
            if (exists) {
                Toast.makeText(context, "이미 입력된 값입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
    if (showErrorDialog.value) {
        ErrorDialog(showErrorDialog as MutableState<Boolean>, errorMessage)
    }

    PermissionDeniedDialog(
        showDialog = showPermissionDialog.value,
        { viewModel.updateShowPermissionDialog(false) },
        { navigateToSettings(context); viewModel.updateShowPermissionDialog(false) })
}

