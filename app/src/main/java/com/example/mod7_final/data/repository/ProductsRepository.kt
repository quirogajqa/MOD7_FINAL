package com.example.mod7_final.data.repository

import com.example.mod7_final.data.local.ProductDao
import com.example.mod7_final.data.local.toProductResponse
import com.example.mod7_final.data.local.toProductoEntity
import com.example.mod7_final.data.models.ProductDataSource
import com.example.mod7_final.data.remote.ProductsApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val api: ProductsApi,
    private val productDao: ProductDao
){
    suspend fun getProducts (): Result<ProductDataSource> {
        return try {
            val response = api.getProducts(

            )
            val responseConverted = response.map { it ->
                it.toProductoEntity()
            }
            productDao.deleteAllProducts()
            productDao.insertProducts(responseConverted)

            Result.success(
                ProductDataSource (
                    productos = response,
                    totalResults = response.size,
                    isFromCache = false
                )
            )
        } catch (e: Exception) {
            val cachedProduct = productDao.getAllProducts().map { it.toProductResponse() }

            if (cachedProduct.isNotEmpty()) {
                Result.success(
                    ProductDataSource(
                        productos = cachedProduct,
                        totalResults = cachedProduct.size,
                        isFromCache = false
                    )
                )
            } else {
                // No hay cache, retornar error
                Result.failure(e)
            }
        }
    }

    suspend fun refreshProducs(): Result<ProductDataSource> {
        return getProducts()
    }
}