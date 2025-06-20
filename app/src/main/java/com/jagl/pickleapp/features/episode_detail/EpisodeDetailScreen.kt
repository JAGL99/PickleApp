package com.jagl.pickleapp.features.episode_detail

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
fun EpisodeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler { onBack() }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        EpisodeDetailScreenContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState
        )

    }

}

@Composable
fun EpisodeDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: UiState
) {
    val character = uiState.data ?: return
    CharacterDetailItem(
        modifier = modifier,
        item = character
    )
}

