package com.example.bonsai_application.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.*

@Composable
fun HomeScreen(rootNavController: NavController) {
    val bottomItems = listOf(
        NavItem("home", "Trang chủ", Icons.Default.Home),
        NavItem("favorites", "Yêu thích", Icons.Default.Favorite),
        NavItem("Notification", "Thông báo", Icons.Default.Notifications),
        NavItem("account", "Tài khoản", Icons.Default.Person)
    )


    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = bottomNavController.currentBackStackEntryAsState().value?.destination?.route
                bottomItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            bottomNavController.navigate(item.route) {
                                popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (currentRoute == item.route) Color(0xFF2E7D32) else Color.Gray // Xanh lá cây khi chọn
                            )
                        },
                        label = {
                            Text(
                                text = item.label,
                                color = if (currentRoute == item.route) Color(0xFF2E7D32) else Color.Gray,
                                fontWeight = if (currentRoute == item.route) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFDFF5E1), // Nền xanh nhạt khi chọn
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            selectedIconColor = Color(0xFF2E7D32), // Xanh lá đậm
                            selectedTextColor = Color(0xFF2E7D32)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = bottomNavController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") {
                Trangchu("Trang chủ BonSai", navController = rootNavController)
            }
            composable("favorites") {
                Danhmuc("favorites")
            }
            composable("Notification") {
                Danhmuc("Notification")
            }
            composable("account") { Taikhoan("Tài khoản") }
        }
    }
}

data class NavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun Danhmuc(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}
@Composable
fun Giohang(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}
@Composable
fun Taikhoan(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, style = MaterialTheme.typography.headlineMedium)
    }
}
