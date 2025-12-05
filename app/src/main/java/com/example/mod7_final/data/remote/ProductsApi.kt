package com.example.mod7_final.data.remote

import com.example.mod7_final.data.models.Product
import retrofit2.http.GET

interface ProductsApi {
    @GET("products")
    suspend fun getProducts( ): List<Product>
}