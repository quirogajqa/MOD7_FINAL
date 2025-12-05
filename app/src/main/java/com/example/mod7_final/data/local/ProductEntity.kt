package com.example.mod7_final.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mod7_final.data.models.ProductResponse
import com.example.mod7_final.data.models.Producto
import com.example.mod7_final.data.models.Rating
import java.util.UUID
import kotlin.Int

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val numberId: String = UUID.randomUUID().toString(),
    val id: Int,
    val nombre: String,
    val cantidad: Int,
    val precio: Double
)

fun ProductEntity.toProducto(): Producto{
    return Producto(
        id = id,
        nombre = nombre,
        cantidad = cantidad,
        precio = precio
    )
}

fun Producto.toProductoEntity(): ProductEntity{
    return ProductEntity(
        id = id,
        nombre = nombre,
        cantidad = cantidad,
        precio = precio
    )
}


fun ProductEntity.toProductResponse(): ProductResponse{
    return ProductResponse(
        id = id,
        title = nombre,
        price = precio,
        description = "",
        category = "",
        image = "",
        rating = Rating(0.0,0)
    )
}

fun ProductResponse.toProductoEntity(): ProductEntity{
    return ProductEntity(
        id = id,
        nombre = title,
        cantidad = 1,
        precio = price
    )
}