package com.example.mod7_final.view.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mod7_final.data.local.ProductEntity
import com.example.mod7_final.data.models.ProductResponse
import com.example.mod7_final.data.models.Producto
import com.example.mod7_final.ui.theme.AppShape
import com.example.mod7_final.ui.theme.Slate200
import com.example.mod7_final.viewmodel.ProductViewModel


@Composable
fun ProductCard(
    producto: ProductEntity,
    viewModel: ProductViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = AppShape.medium,
        colors = CardDefaults.cardColors(
            containerColor = Slate200
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(Modifier.padding(vertical = 15.dp, horizontal = 30.dp)) {
            Column(
                Modifier.weight(7f)
            ) {
                Row {
                    Text(
                        text = "ID: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(51.dp))
                    Text(
                        text = producto.id.toString(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Nombre: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(14.dp))
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Cantidad: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = "cantidad",
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
                Spacer(Modifier.height(8.dp))
                Row {
                    Text(
                        text = "Precio: ",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.width(25.dp))
                    Text(
                        text = producto.precio.toString(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            IconButton(
                onClick = { viewModel.onProductoDeleted(producto) },
                Modifier
                    .align(Alignment.Top)
                    .weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar"
                )
            }
        }
    }
}