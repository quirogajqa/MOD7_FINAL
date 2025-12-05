package com.example.mod7_final.data.models

data class ProductDataSource (
    val productos: List<ProductResponse>,
    val totalResults: Int,
    val isFromCache: Boolean
)

