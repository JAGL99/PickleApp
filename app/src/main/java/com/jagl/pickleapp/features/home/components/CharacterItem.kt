package com.jagl.pickleapp.features.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jagl.pickleapp.core.utils.ui.components.CharacterImage
import com.jagl.pickleapp.core.utils.ui.components.CharacterImageContainer
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
            .width(150.dp)
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
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp, horizontal = 6.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 24.sp,
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
                location = "Citadel of Ricks",
                episodes = emptyList()
            ),
            onClick = {}
        )
    }
}