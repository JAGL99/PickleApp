package com.jagl.pickleapp.features.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.features.home.components.CharacterItem
import com.jagl.pickleapp.features.home.components.FullScreenLoading
import kotlinx.coroutines.flow.SharedFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val uiEvent = viewModel.uiEvent
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { errorMessage ->
            Toast.makeText(
                context,
                errorMessage,
                Toast.LENGTH_SHORT
            )
        }
    }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        HomeContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            uiEvent = uiEvent,
            getCharacters = viewModel::getMoreCharacters
        )

    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: UiState,
    uiEvent: SharedFlow<UiEvent>,
    getCharacters: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.then(modifier)
    ) {

        if (uiState.isLoading) {
            FullScreenLoading()
        }

        val data = uiState.data

        LazyVerticalGrid(
            columns = GridCells.Fixed(2) // 2 columnas
        ) {
            itemsIndexed(items = data) { index, character ->
                if (!uiState.isLoading && ((index + 1) >= (uiState.page * 20))) {
                    getCharacters()
                }
                CharacterItem(item = character)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    //val uiState = UiState.Success(listOf())
    //HomeContent(uiState = uiState, uiEvent = uiEvent)
}

