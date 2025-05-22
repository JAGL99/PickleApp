package com.jagl.pickleapp.features.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.features.detail.components.CharacterDetailItem

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler { onBack() }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        DetailScreenContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState
        )

    }

}

@Composable
fun DetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: UiState
) {
    val character = uiState.data ?: return
    CharacterDetailItem(
        modifier = modifier,
        item = character
    )
}

