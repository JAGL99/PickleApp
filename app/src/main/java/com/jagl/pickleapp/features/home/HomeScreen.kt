package com.jagl.pickleapp.features.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
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
import com.jagl.pickleapp.features.home.components.EpisodeItem
import com.jagl.pickleapp.features.home.components.FullScreenLoading

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (Long) -> Unit
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
            ).show()
        }
    }

    LaunchedEffect(uiEvent) {
        uiEvent.collect { event ->
            when (event) {
                is UiEvent.GoToEpisodeDetails -> onNavigateToDetail(event.episodeId)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        HomeContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            getMoreCharacters = viewModel::getMoreEpisodes,
            onClick = viewModel::onGoToDetail
        )

    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier.fillMaxSize(),
    uiState: UiState,
    getMoreCharacters: () -> Unit,
    onClick: (id: Long) -> Unit,
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
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(1)
        ) {
            itemsIndexed(items = data) { index, episode ->
                if (!uiState.isLoading && ((index + 1) >= (uiState.page * 20))) {
                    getMoreCharacters()
                }
                EpisodeItem(item = episode, onClick = onClick)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val uiState = UiState()
    HomeContent(uiState = uiState, onClick = {}, getMoreCharacters = {})
}

