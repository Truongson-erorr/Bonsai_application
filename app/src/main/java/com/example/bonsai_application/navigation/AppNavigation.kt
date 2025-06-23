package com.example.bonsai_application.navigation

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.bonsai_application.view.LoginScreen
import com.example.bonsai_application.view.RegisterScreen
import com.example.bonsai_application.view.Trangchu
import com.example.bonsai_application.viewmodel.CategoryViewModel
import com.example.bonsai_application.viewmodel.ProductViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bonsai_application.view.CartScreen
import com.example.bonsai_application.view.HomeScreen
import com.example.bonsai_application.view.ProductDetailScreen
import java.lang.reflect.Modifier

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }

        composable(
            "product_detail/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: return@composable
            val productViewModel: ProductViewModel = viewModel()

            LaunchedEffect(productId) {
                productViewModel.loadProductDetails(productId)
            }

            val product by productViewModel.selectedProduct.collectAsState()
            val isLoading by productViewModel.isLoading.collectAsState()
            val error by productViewModel.errorMessage.collectAsState()

            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text("Lỗi: $error", color = MaterialTheme.colorScheme.error)
                product != null -> ProductDetailScreen(product = product!!)
            }
        }

        composable("cart") {
            CartScreen(navController)
        }

    }
}