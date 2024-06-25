package com.inconus.mealmanagement.ui

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
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

    BottomNavigation (backgroundColor = Color.White) {
        val currentRoute = navController.currentRoute()
        items.forEach { screen ->
            // 선택된 아이템과 현재 루트비교
            val isSelected = currentRoute == screen.route
            BottomNavigationItem(modifier = Modifier.padding(vertical = 5.dp),
                icon = {
                    // 선택 여부에 따라 아이콘 변경
                    Icon(
                        painter = if (isSelected) screen.iconSelected.invoke() else screen.iconUnselected.invoke(),
                        contentDescription = null,
                        tint = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black,
                        modifier = Modifier.padding(bottom = 5.dp).size(20.dp)
                    )
                },
                label = {
                    Text(
                        stringResource(id = screen.titleResId),
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Black
                    )
                },
                selected = currentRoute == screen.route,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    // QR Permission 스크린 클릭 시 권한 확인
                    if (screen.route == "qrPermission") {
                        if (qrViewModel.hasCameraPermission.value != true){
                            navController.popBackStack(
                                navController.graph.startDestinationId,
                                inclusive = false
                            )
                            Log.d("확인", "BottmNav - 백스택 삭제")
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