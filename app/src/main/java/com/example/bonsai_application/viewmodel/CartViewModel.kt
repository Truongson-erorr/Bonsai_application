package com.example.bonsai_application.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.bonsai_application.model.CartItem
import com.example.bonsai_application.model.Product
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    private val _cartItems = mutableStateListOf<CartItem>()
    val cartItems: List<CartItem> = _cartItems

    fun loadCartItems() {
        db.collection("Cart")
            .document(userId)
            .collection("items")
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    _cartItems.clear()
                    for (doc in it.documents) {
                        val item = doc.toObject(CartItem::class.java)
                        if (item != null) _cartItems.add(item.copy(id = doc.id))
                    }
                }
            }
    }


    fun addToCart(product: Product, size: String, quantity: Int) {
        val cartRef = db.collection("Cart")
            .document(userId)
            .collection("items")

        // Tìm sản phẩm trùng cả ID và Size
        cartRef.whereEqualTo("productId", product.id)
            .whereEqualTo("size", size)
            .get()
            .addOnSuccessListener { snapshot ->
                if (!snapshot.isEmpty) {
                    val doc = snapshot.documents.first()
                    val existingQty = doc.getLong("quantity") ?: 1
                    cartRef.document(doc.id).update("quantity", existingQty + quantity)
                } else {
                    val newItem = CartItem(
                        productId = product.id,
                        productName = product.name,
                        productImage = product.imageUrl,
                        price = product.price,
                        quantity = quantity,
                        size = size,
                        userId = userId
                    )
                    cartRef.add(newItem)
                }
            }
    }


    fun removeFromCart(cartItemId: String) {
        db.collection("Cart")
            .document(userId)
            .collection("items")
            .document(cartItemId)
            .delete()
    }

    fun updateQuantity(cartItemId: String, newQuantity: Int) {
        if (newQuantity > 0) {
            db.collection("Cart")
                .document(userId)
                .collection("items")
                .document(cartItemId)
                .update("quantity", newQuantity)
        } else {
            removeFromCart(cartItemId) // Xóa nếu số lượng bằng 0
        }
    }

    fun clearCart() {
        val cartRef = db.collection("Cart").document(userId).collection("items")
        cartRef.get().addOnSuccessListener { snapshot ->
            for (doc in snapshot.documents) {
                cartRef.document(doc.id).delete()
            }
        }
    }

}
