package com.sandeepgupta.gratitude.presentation.ui.components

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sandeepgupta.gratitude.R
import com.sandeepgupta.gratitude.domain.model.CardModel
import com.sandeepgupta.gratitude.presentation.ui.theme.dividerColor
import com.sandeepgupta.gratitude.presentation.ui.theme.primaryFixed
import com.sandeepgupta.gratitude.util.getListOfInstalledSocialMediaApps
import com.sandeepgupta.gratitude.util.getUriFromImageUrl
import com.sandeepgupta.gratitude.util.intent


@Composable
fun ShareBottomSheet(
    cardItem: CardModel,
    context: Context,
    onCloseClick: () -> Unit,
    onCopyClick: () -> Unit
) {
    var isCopied by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "Inspire Your Friends",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = { onCloseClick() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(painterResource(id = R.drawable.ic_close), contentDescription = "Close Button")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = cardItem.dzImageUrl,
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1F)
                .clip(RoundedCornerShape(12.dp))
                .border(
                    1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .shadow(10.dp),
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 20.dp)
                .background(primaryFixed, shape = RoundedCornerShape(50.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (cardItem.text == "") cardItem.articleUrl else "\"${cardItem.text}\" ${cardItem.author}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )

            if (isCopied) {
                Button(onClick = {
                    onCopyClick()
                    isCopied = false
                }, Modifier.padding(horizontal = 4.dp)) {
                    Text(
                        text = "Copied",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                OutlinedButton(
                    onClick = {
                        onCopyClick()
                        isCopied = true
                    },
                    modifier = Modifier.padding(horizontal = 4.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Text(
                        text = "Copy",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(dividerColor)
        )

        Text(
            text = "Share to",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        LazyRow(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            // Social Media Buttons
            items(
                items = getListOfInstalledSocialMediaApps(context),
                key = { it.packageName }) { app ->
                SocialMediaButton(app) {
                    getUriFromImageUrl(
                        context = context,
                        imageUrl = cardItem.dzImageUrl,
                    ) {
                        val shareIntent = intent(it, cardItem)
                        shareIntent.setPackage(app.packageName)
                        context.startActivity(shareIntent)
                    }
                }
            }

            //more button
            item {
                ShareMoreButton {
                    getUriFromImageUrl(
                        context = context,
                        imageUrl = cardItem.dzImageUrl,
                    ) {
                        val shareIntent = intent(it, cardItem)
                        shareIntent.setPackage(null)
                        context.startActivity(
                            Intent.createChooser(
                                shareIntent,
                                "Share with"
                            )
                        )
                    }
                }
            }
        }

    }
}
