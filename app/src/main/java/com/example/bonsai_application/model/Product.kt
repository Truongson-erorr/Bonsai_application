package com.example.bonsai_application.model

data class Product(
    val id: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val description: String ="",
    val categoryId: String = "",
    val size: String = " ",
    val quantityInStock: Int = 0,
    val price: Double = 0.0,
    val discountPercent: Double = 0.0
)