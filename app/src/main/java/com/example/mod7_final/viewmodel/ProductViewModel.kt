package com.example.mod7_final.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mod7_final.data.local.ProductDao
import com.example.mod7_final.data.local.ProductEntity
import com.example.mod7_final.data.local.toProductoEntity
import com.example.mod7_final.data.models.ProductResponse
import com.example.mod7_final.data.models.Producto
import com.example.mod7_final.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: ProductsRepository,
    private val productDao: ProductDao
): ViewModel() {

    private val _uiState = MutableStateFlow(ProductoUiState())

    val uiState: StateFlow<ProductoUiState> = _uiState

    private val _productsList = MutableStateFlow<List<ProductEntity>>(emptyList())
    val productsList: StateFlow<List<ProductEntity>> = _productsList

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            repository.getProducts().fold(
                onSuccess = { dataSource ->

                    _productsList.value = dataSource.productos.map { it.toProductoEntity() }
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null,
                            isFromCache = dataSource.isFromCache,
                        )
                    }
                },

                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = exception.message ?: "Error desconocido"
                        )
                    }

                }
            )
        }
    }

    fun retry() {
        _uiState.update {
            it.copy(
                isLoading = false
            )
        }
        loadProducts()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRefreshing = true) }

            repository.refreshProducs().fold(
                onSuccess = { dataSource ->
                    _productsList.value = dataSource.productos.map { it.toProductoEntity() }
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            errorMessage = null,
                            isFromCache = dataSource.isFromCache,
                        )
                    }
                },
                onFailure = { exception ->
                    _uiState.update {
                        it.copy(
                            isRefreshing = false,
                            errorMessage = exception.message ?: "Error al refrescar"
                        )
                    }
                }
            )
        }
    }

    fun onNombreChanged(nombre: String) {
        _uiState.update {
            _uiState.value.copy( nombre = nombre.trimStart())
        }

        verifyInput()
    }
    fun onPrecioChanged(precioString: String) {
        try {
            val precio = if (precioString.isEmpty()) 0.0 else precioString.toDouble()
            _uiState.update {
                _uiState.value.copy(precio = precio)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $precioString no es un número válido.")
        }

    }

    fun onCantidadChanged(cantidadString: String) {
        try {
            val cantidad = if (cantidadString.isEmpty()) 0 else cantidadString.toInt()
            _uiState.update {
                _uiState.value.copy(cantidad = cantidad)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $cantidadString no es un número válido.")
        }

    }

    fun onIdChanged(idString: String) {
        try {
            val id = if (idString.isEmpty()) 0 else idString.toInt()
            _uiState.update {
                _uiState.value.copy(id = id)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $idString no es un número válido.")
        }

    }

    private fun verifyInput() {
        val enabledAdd = isNombreValid(_uiState.value.nombre)
                && isPrecioValid(_uiState.value.precio)
                && isIdValid(_uiState.value.id)
                && isCantidadValid(_uiState.value.cantidad)
        _uiState.update {
            it.copy(isButtonAddEnabled = enabledAdd)
        }
        verifyClear()
    }

    private fun verifyClear() {
        val producto = _uiState.value
        val isEnabledClear = producto.nombre.isNotEmpty()
                || producto.precio != 0.0
                || producto.cantidad != 0
                || producto.id != 0

        _uiState.update {
            it.copy(isEnabledClear = isEnabledClear)
        }
    }

    fun onShowDialog(showDialog: Boolean){
        _uiState.update {
            _uiState.value.copy( showDialog = showDialog)
        }
    }

    fun onProductoAdded() {
        val newArticuloComparado = ProductEntity(
            id = _uiState.value.id,
            nombre = _uiState.value.nombre,
            cantidad = _uiState.value.cantidad,
            precio = _uiState.value.precio
            )
        _productsList.update { currentList ->
            currentList + newArticuloComparado
        }

        _uiState.update {
            _uiState.value.copy( isButtonAddEnabled = false )
        }

        _uiState.update { ProductoUiState() }

        viewModelScope.launch {
            productDao.insertProduct(newArticuloComparado)
        }
    }

    fun clearShowDialog(){
        _uiState.update { ProductoUiState( showDialog = true ) }
    }

    fun onProductoDeleted(Producto: ProductEntity) {
        viewModelScope.launch {
            productDao.deleteProductById(Producto.id)
        }
        _productsList.update { currentList ->
            currentList - Producto
        }
    }
}

private fun isIdValid(id: Int): Boolean = id >= 0
private fun isNombreValid(nombre: String): Boolean = nombre.length >= 2

private fun isCantidadValid(cantidad: Int): Boolean = cantidad >= 0
private fun isPrecioValid(precio: Double): Boolean = precio > 0.0

data class ProductoUiState(
    val id: Int = 0,
    val nombre: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
    val showDialog: Boolean = false,
    val isEnabledClear: Boolean = false,
    val isButtonAddEnabled: Boolean = false,
    val isFromCache: Boolean = false,
)