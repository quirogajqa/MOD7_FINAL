package com.example.mod7_final.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod7_final.R
import com.example.mod7_final.ui.theme.Amber400
import com.example.mod7_final.ui.theme.Indigo950
import com.example.mod7_final.ui.theme.Pink40
import com.example.mod7_final.ui.theme.Slate200
import com.example.mod7_final.view.home.components.AgregarProductoScreen
import com.example.mod7_final.view.home.components.ProductCard
import com.example.mod7_final.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen (
    viewModel: ProductViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val productList by viewModel.productsList.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Lista de productos")
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Indigo950,
                    titleContentColor = Slate200
                ),
                modifier = Modifier.clickable {
                    coroutineScope.launch { listState.animateScrollToItem(0) }
                }
            )
        },
        floatingActionButton = {
            SmallFloatingActionButton(
                onClick = { viewModel.onShowDialog(true)  },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar un producto",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Amber400)
                    }
                }

                uiState.errorMessage != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Slate200)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(R.string.home_error_loading),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Pink40
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = uiState.errorMessage ?: "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Pink40
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.retry() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Indigo950
                            )
                        ) {
                            Text(stringResource(R.string.home_retry))
                        }
                    }
                }

                else -> {
                    Column(
                        Modifier.padding(12.dp)
                    ) {
                        LazyColumn(
                            state = listState,
                            verticalArrangement = Arrangement
                                .spacedBy(12.dp)
                        ) {
                            items(
                                items = productList
                            ) { product ->
                                ProductCard(
                                    producto = product
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (uiState.showDialog) {
        val closeSheetAction: () -> Unit = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    viewModel.onShowDialog(false)
                }
            }
        }

        ModalBottomSheet(
            onDismissRequest = closeSheetAction,
            Modifier
                .padding(8.dp),
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 2.dp,
        ) {
            Column(
                Modifier
                    .verticalScroll(scrollState)
            ) {
                AgregarProductoScreen(
                    viewModel = viewModel,
                    onCloseSheet = closeSheetAction
                )
            }
        }
    }

}