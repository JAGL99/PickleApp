package com.jagl.pickleapp.features.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.jagl.pickleapp.R
import com.jagl.pickleapp.domain.model.CharacterDomain
import com.jagl.pickleapp.ui.theme.PickleAppTheme

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    item: CharacterDomain,
    onClick: (id: Long) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable(onClick = { onClick(item.id) }),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CharacterImageContainer(
                modifier = Modifier.fillMaxWidth()
            ) {
                CharacterImage(item.image)
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = modifier.fillMaxWidth().padding(2.dp)
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = "${item.status} - ${item.species}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.first_seen_in),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.origin,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.last_known_location),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.location,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun CharacterImage(image: String) {
    Box {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .size(Size.ORIGINAL)
                .build()
        )
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CharacterImageContainer(
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    Surface(modifier.aspectRatio(1f), RoundedCornerShape(4.dp)) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCharacterItem() {
    PickleAppTheme {
        CharacterItem(
            item = CharacterDomain(
                id = 1,
                name = "Rick Sanchez",
                status = "Alive",
                species = "Human",
                image = "",
                origin = "Earth (C-137)",
                location = "Citadel of Ricks"
            ),
            onClick = {}
        )
    }
}