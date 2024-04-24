package com.inconus.mealmanagement.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.inconus.mealmanagement.nav.currentRoute
import com.inconus.mealmanagement.util.Screen

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.QrScanner,
        Screen.MyPage,
        Screen.Calculate
    )

    BottomNavigation {
        val currentRoute =navController.currentRoute()
        items.forEach { screen ->
            BottomNavigationItem(
                icon = { Icon(screen.icon, contentDescription = null) },
                label = { Text(screen.title) },
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview(){
    val navController = rememberNavController()
    BottomNavigationBar(navController)
}