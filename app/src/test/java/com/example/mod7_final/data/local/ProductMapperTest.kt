package com.example.mod7_final.data.local

import org.junit.Assert.assertEquals
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `toProducto convierte correctamente una Entidad a Modelo de dominio`() {

        val entity = ProductEntity(
            id = 1,
            nombre = "Gaseosa",
            cantidad = 20,
            precio = 1500.0,
            numberId = "abc-123"
        )

        val producto = entity.toProducto()

        assertEquals(entity.id, producto.id)
        assertEquals(entity.nombre, producto.nombre)
        assertEquals(entity.cantidad, producto.cantidad)
        assertEquals(entity.precio, producto.precio, 0.0)
    }
}