package com.example.bonsai_application.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bonsai_application.model.Category
import com.example.bonsai_application.viewmodel.CategoryViewModel
import com.example.bonsai_application.viewmodel.ProductViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay

@Composable
fun Trangchu(
    text: String,
    navController: NavController,
    viewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    productViewModel: ProductViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    var fullName by remember { mutableStateOf<String?>(null) }

    val bannerImages = listOf(
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1750311808/jwmtrmqfvdvvynexxvc5.png",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1750311808/tuc28drd5k48jnnds90l.jpg",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1750312010/i3zx6msqhojainagqlux.jpg",
        "https://res.cloudinary.com/dq64aidpx/image/upload/v1750312182/riktte6l44esqrlwrypw.png"
    )

    var currentIndex by remember { mutableStateOf(0) }
    val categoryList by viewModel.categories.collectAsState()
    val allCategory = remember { Category(id = "", name = "Tất cả", imageUrl = "https://res.cloudinary.com/dq64aidpx/image/upload/v1750403211/usingcwlpgteh0fk5zbd.png") }
    val categoriesWithAll = listOf(allCategory) + categoryList

    var selectedCategoryId by remember { mutableStateOf<String?>(null) }

    val products by productViewModel.productList.collectAsState()
    val isLoading by productViewModel.isLoading.collectAsState()
    val errorMessage by productViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            currentIndex = (currentIndex + 1) % bannerImages.size
        }
    }

    LaunchedEffect(Unit) {
        productViewModel.loadProductsByCategory()
    }

    LaunchedEffect(userId) {
        if (userId != null) {
            FirebaseFirestore.getInstance()
                .collection("Users")
                .document(userId)
                .get()
                .addOnSuccessListener { document ->
                    fullName = document.getString("fullName")
                }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(modifier = Modifier
                .fillMaxWidth())
            {
                Text(text = text, style = MaterialTheme.typography.headlineMedium)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when {
                        userId == null -> Text("Bạn chưa đăng nhập", style = MaterialTheme.typography.bodyLarge)
                        fullName != null -> Text("Xin chào, $fullName", style = MaterialTheme.typography.bodyLarge)
                        else -> CircularProgressIndicator()
                    }

                    IconButton(onClick = {
                        navController.navigate("cart")
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Giỏ hàng"
                        )
                    }
                }
                AsyncImage(
                    model = bannerImages[currentIndex],
                    contentDescription = "Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .clip(RoundedCornerShape(12.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))
                Text("Danh mục", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(categoriesWithAll) { category ->
                        val isSelected = selectedCategoryId == category.id || (selectedCategoryId == null && category.id == "")
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .width(100.dp)
                                .clickable {
                                    selectedCategoryId = if (category.id.isEmpty()) null else category.id
                                    productViewModel.loadProductsByCategory(selectedCategoryId)
                                }
                        ) {
                            Card(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(50)),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (isSelected)
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    else
                                        MaterialTheme.colorScheme.surface
                                )
                            ) {
                                if (category.imageUrl.isNotEmpty()) {
                                    AsyncImage(
                                        model = category.imageUrl,
                                        contentDescription = category.name,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("All", fontSize = 12.sp)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = category.name,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text("Tất cả sản phẩm", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(2.dp))
            }
        }

        if (isLoading) {
            item {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        } else if (!errorMessage.isNullOrEmpty()) {
            item {
                Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error)
            }
        } else {
            items(products.chunked(2)) { rowProducts ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowProducts.forEach { product ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(240.dp)
                                .clickable {
                                    navController.navigate("product_detail/${product.id}")
                                },
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                AsyncImage(
                                    model = product.imageUrl,
                                    contentDescription = product.name,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(product.name, color = Color.Black, fontWeight = FontWeight.Bold, maxLines = 2)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Giá: ${product.price}đ", style = MaterialTheme.typography.bodySmall)
                                if (product.quantityInStock < 5) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFFFEBEE))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "Còn giới hạn",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFFD32F2F)
                                        )
                                    }
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(Color(0xFFDFF5E1))
                                            .padding(horizontal = 6.dp, vertical = 2.dp)
                                    ) {
                                        Text(
                                            text = "Còn hàng",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color(0xFF2E7D32)
                                        )
                                    }
                                }
                                if (product.discountPercent > 0) {
                                    Text(
                                        "Giảm ${product.discountPercent}%",
                                        color = Color.Red,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                    if (rowProducts.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
