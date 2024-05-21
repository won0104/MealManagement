package com.inconus.mealmanagement.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.nav.currentRoute
import com.inconus.mealmanagement.util.Screen
import com.inconus.mealmanagement.vm.QrViewModel

@Composable
fun BottomNavigationBar(navController: NavHostController, qrViewModel: QrViewModel) {
    val items = listOf(
        Screen.QrPermission,
        Screen.Calculate,
        Screen.MyPage
    )

    BottomNavigation {
        val currentRoute = navController.currentRoute()
        items.forEach { screen ->
            // 선택된 아이템과 현재 루트비교
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(
                icon = {
                    // 선택 여부에 따라 아이콘 색상 변경
                    val iconColor =
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
                    Icon(
                        painter = screen.icon.invoke(),
                        contentDescription = null,
                        tint = iconColor
                    )
                },
                label = {
                    Text(
                        screen.title,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 10.sp)
                    )
                },
                selected = currentRoute == screen.route,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.background(Color(0xFFF3EDF7)),
                onClick = {
                    // QR Permission 스크린 클릭 시 권한 확인
                    if (screen.route == "qrPermission") {
                        if (!qrViewModel.hasCameraPermission.value!!) {
                            navController.popBackStack(
                                navController.graph.startDestinationId,
                                inclusive = false
                            )
                            Log.d("확인용", "백스택 삭제")
                        }
                    }
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = false // 화면 초기 상태로 리셋
                            }
                            launchSingleTop = true // 최상위 화면 재사용
                        }
                    }
                }
            )
        }
    }
}


//