package com.example.mod7_final.viewmodel

import com.example.mod7_final.data.local.ProductDao
import com.example.mod7_final.data.repository.ProductsRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onNombreChanged actualiza el estado y elimina espacios al inicio`() {

        val repository = mockk<ProductsRepository>(relaxed = true)
        val dao = mockk<ProductDao>(relaxed = true)

        val viewModel = ProductViewModel(repository, dao)

        viewModel.onNombreChanged("  Hola ")

        assertEquals("Hola ", viewModel.uiState.value.nombre)
    }
}