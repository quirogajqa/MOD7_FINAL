package com.example.mod7_final.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ProductDaoTest {

    private lateinit var db: ProductDatabase
    private lateinit var dao: ProductDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ProductDatabase::class.java).build()
        dao = db.productDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    // Test Unitario 1: Insertar y recuperar
    @Test
    fun insertProductAndReadList() = runBlocking {
        val product = ProductEntity(id = 1, nombre = "Prueba", cantidad = 5, precio = 100.0)
        dao.insertProduct(product)

        val products = dao.getAllProducts()

        assertEquals(1, products.size)
        assertEquals("Prueba", products[0].nombre)
    }

    // Test Unitario 2: Eliminar
    @Test
    fun deleteProductAndVerifyEmpty() = runBlocking {
        val product = ProductEntity(id = 2, nombre = "Borrar", cantidad = 1, precio = 50.0)
        dao.insertProduct(product)
        dao.deleteProductById(2)

        val products = dao.getAllProducts()
        assertTrue(products.isEmpty())
    }
}