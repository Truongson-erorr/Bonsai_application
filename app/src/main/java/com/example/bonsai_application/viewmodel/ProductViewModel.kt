package com.example.bonsai_application.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bonsai_application.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    fun loadProductsByCategory(categoryId: String? = null) {
        _isLoading.value = true
        _errorMessage.value = null

        val query = if (categoryId != null) {
            db.collection("Products").whereEqualTo("categoryId", categoryId)
        } else {
            db.collection("Products")
        }

        query.get()
            .addOnSuccessListener { result ->
                val products = result.documents.mapNotNull { it.toObject(Product::class.java) }
                _productList.value = products
                _isLoading.value = false
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Lỗi khi tải sản phẩm: ${e.message}"
                _isLoading.value = false
            }
    }

    fun loadProductDetails(productId: String) {
        _isLoading.value = true
        _errorMessage.value = null
        _selectedProduct.value = null

        db.collection("Products")
            .document(productId)
            .get()
            .addOnSuccessListener { document ->
                val product = document.toObject(Product::class.java)
                if (product != null) {
                    _selectedProduct.value = product
                } else {
                    _errorMessage.value = "Sản phẩm không tồn tại"
                }
                _isLoading.value = false
            }
            .addOnFailureListener { e ->
                _errorMessage.value = "Lỗi khi tải chi tiết sản phẩm: ${e.message}"
                _isLoading.value = false
            }
    }

    // KHÔNG dùng trong màn detail nữa
    fun findProductById(productId: String?): Product? {
        return productList.value.find { it.id == productId }
    }


}

