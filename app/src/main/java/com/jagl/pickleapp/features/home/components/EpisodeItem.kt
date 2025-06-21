package com.jagl.pickleapp.features.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagl.pickleapp.domain.model.EpisodeDomain
import com.jagl.pickleapp.ui.theme.PickleAppTheme

@Composable
fun EpisodeItem(
    modifier: Modifier = Modifier,
    item: EpisodeDomain,
    onClick: (id: Long) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = { onClick(item.id) }),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Blue
                )
                .padding(6.dp),
        ) {
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Column(
                modifier = modifier
                    .wrapContentWidth()
                    .padding(vertical = 2.dp, horizontal = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val splitAirDate = item.airDate.split(",")
                val monthAndDay = splitAirDate.firstOrNull() ?: "N/A"
                val year = splitAirDate.lastOrNull() ?: "N/A"
                Text(
                    text = year,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = monthAndDay,
                    fontSize = 20.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp, horizontal = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Episode ${item.episode}",
                    fontSize = 24.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Name ${item.name}",
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewEpisodeItem() {
    PickleAppTheme {
        EpisodeItem(
            item = EpisodeDomain(
                id = 1L,
                name = "Pilot",
                airDate = "December 2, 2013",
                episode = "S01E01",
                charactersInEpisode = emptyList(),
                url = "",
                created = "",
                page = 1
            ),
            onClick = {}
        )
    }
}