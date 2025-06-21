package com.jagl.pickleapp.features.episode_detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.domain.model.EpisodeDomain
import com.jagl.pickleapp.features.home.components.CharacterItem

@Composable
fun EpisodeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: EpisodeDetailViewModel = hiltViewModel(),
    onNavigateToCharacterDetail: (Long) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    BackHandler { onBack() }
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        EpisodeDetailScreenContent(
            modifier = modifier.padding(innerPadding),
            uiState = uiState,
            onNavigateToCharacterDetail = onNavigateToCharacterDetail
        )

    }

}

@Composable
fun EpisodeDetailScreenContent(
    modifier: Modifier = Modifier,
    uiState: UiState,
    onNavigateToCharacterDetail: (Long) -> Unit
) {
    val episode = uiState.data ?: return
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Episode Details",
            modifier = Modifier.padding(16.dp)
        )
        val name = episode.name
        Text(
            text = "Name: $name",
            modifier = Modifier.padding(16.dp)
        )
        val airDate = episode.airDate
        Text(
            text = "Air Date: $airDate",
            modifier = Modifier.padding(16.dp)
        )
        val episodeNumber = episode.episode
        Text(
            text = "Episode Number: $episodeNumber",
            modifier = Modifier.padding(16.dp)
        )

        val characters = uiState.charactersInEpisode
        if (characters.isNotEmpty()) {
            Text(
                text = "Characters in this episode:",
                modifier = Modifier.padding(16.dp)
            )
            Row(
                modifier = Modifier
                    .height(150.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                characters.forEach { character ->
                    CharacterItem(
                        item = character,
                        onClick = {
                            onNavigateToCharacterDetail(it)
                        }
                    )
                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun EpisodeDetailScreenPreview() {
    val uiState = UiState(
        data = EpisodeDomain(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01",
            charactersInEpisode = emptyList(),
            url = "https://rickandmortyapi.com/api/episode/1",
            created = "2013-11-10T12:56:33.798Z"
        ),
        isLoading = false,
        errorMessage = null,
        charactersInEpisode = listOf(
            CharacterDomain(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = "",
                location = "",
                episodes = emptyList()
            )
        )
    )
    EpisodeDetailScreenContent(
        uiState = uiState,
        onNavigateToCharacterDetail = { }
    )
}

