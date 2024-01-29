package com.sandeepgupta.gratitude.presentation.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sandeepgupta.gratitude.R
import com.sandeepgupta.gratitude.domain.model.CardModel

@Composable
fun CardUI(
    cardItem: CardModel,
    context: Context,
    onShareClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(12.dp)
            ),
    ) {
        Text(
            text = cardItem.themeTitle.uppercase(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            color = MaterialTheme.colorScheme.onSurface
        )

        AsyncImage(
            model = cardItem.dzImageUrl,
            contentDescription = "${cardItem.themeTitle} image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
        )


        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            when (cardItem.dzType) {
                "read" -> {
                    CTAButton(R.drawable.ic_read_article, cardItem.primaryCTAText) {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(cardItem.articleUrl)
                        context.startActivity(openURL)
                    }
                }

                "add_affn" -> {
                    CTAButton(R.drawable.ic_read_article, cardItem.primaryCTAText, onClick = {})
                }
            }

            IconButton(
                onClick = onShareClick ,
                Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .size(40.dp)
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_share),
                    contentDescription = "Share Button",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .size(40.dp),
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_bookmark),
                    contentDescription = "Bookmark Button",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun CTAButton(drawable: Int, primaryText: String, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Icon(
            painterResource(id = drawable),
            contentDescription = primaryText
        )
        Text(
            text = primaryText, Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.labelLarge
        )
    }
}