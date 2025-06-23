package com.example.bonsai_application.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.bonsai_application.model.Product
import com.example.bonsai_application.viewmodel.CartViewModel
import com.example.bonsai_application.viewmodel.ProductViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductDetailScreen(
    product: Product,
    navController: NavController? = null
) {
    val scrollState = rememberScrollState()
    val cartViewModel: CartViewModel = viewModel()

    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedQuantity by remember { mutableStateOf(1) }
    var selectedSize by remember { mutableStateOf(product.size.ifBlank { "M" }) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(460.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = product.name,
                fontSize = 20.sp,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Giá: ${formatCurrency(product.price)} đ",
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                )

                if (product.discountPercent > 0) {
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "-${product.discountPercent}%",
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (product.quantityInStock < 5) Color(0xFFFFEBEE) else Color(0xFFDFF5E1)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (product.quantityInStock < 5) "Còn rất ít hàng" else "Còn hàng",
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    color = if (product.quantityInStock < 5) Color(0xFFD32F2F) else Color(0xFF2E7D32)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Giới thiệu về sản phẩm",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = product.description,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium.copy(lineHeight = 20.sp)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Button(
                onClick = { showDialog = true },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Thêm vào giỏ",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text("Thêm", color = Color.White)
            }

            Button(
                onClick = {

                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("Mua ngay", color = Color.White)
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        // Gọi addToCart với các thông tin chọn
                        cartViewModel.addToCart(product, selectedSize, selectedQuantity)
                        Toast.makeText(context, "Đã thêm $selectedQuantity sản phẩm size $selectedSize vào giỏ!", Toast.LENGTH_SHORT).show()
                        showDialog = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Huỷ")
                    }
                },
                title = { Text("Chọn số lượng & size") },
                text = {
                    Column {
                        // Size chọn thủ công (giả sử 3 size: S, M, L)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("S", "M", "L").forEach { size ->
                                OutlinedButton(
                                    onClick = { selectedSize = size },
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        containerColor = if (selectedSize == size) Color.Gray else Color.Transparent
                                    )
                                ) {
                                    Text(size)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        // Số lượng
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Số lượng:")
                            Spacer(modifier = Modifier.width(8.dp))
                            OutlinedTextField(
                                value = selectedQuantity.toString(),
                                onValueChange = {
                                    selectedQuantity = it.toIntOrNull()?.coerceAtLeast(1) ?: 1
                                },
                                singleLine = true,
                                modifier = Modifier.width(80.dp)
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                containerColor = Color.White
            )
        }

    }
}

fun formatCurrency(amount: Double): String {
    val formatter = NumberFormat.getInstance(Locale("vi", "VN"))
    return formatter.format(amount)
}




