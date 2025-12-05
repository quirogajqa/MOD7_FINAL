package com.example.mod7_final.data.repository

import com.example.mod7_final.data.models.ProductDataSource
import com.example.mod7_final.data.remote.ProductsApi

class ProductsRepository (
    private val api: ProductsApi
){

    suspend fun getProducts (): Result<ProductDataSource> {
        return try {
            val response = api.getProducts(

            )

            Result.success(
                ProductDataSource (
                    productos = response
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun refreshProducs(): Result<ProductDataSource> {
        return getProducts()
    }
}