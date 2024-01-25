package com.sandeepgupta.gratitude.ui.components

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.sandeepgupta.gratitude.model.CardModel
import com.sandeepgupta.gratitude.ui.theme.BorderColor
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
    @SuppressLint("UnrememberedMutableState")
    val isCopied = mutableStateOf(false)

    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp))
            .padding(16.dp)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Text(
                text = "Inspire you friends",
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                modifier = Modifier.align(Alignment.CenterStart),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = { onCloseClick() },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close Button")
            }
        }

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
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 20.dp)
                .background(
                    if (isSystemInDarkTheme()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(50.dp)
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (cardItem.text == "") cardItem.articleUrl else cardItem.text + cardItem.author,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 16.dp),
            )

            TextButton(
                onClick = {
                    onCopyClick()
                    isCopied.value = true
                },
                modifier = Modifier
                    .padding(4.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(100.dp)
                    ),
                colors = if (isCopied.value) ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) MaterialTheme.colorScheme.inversePrimary else MaterialTheme.colorScheme.primary
                )
                else ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ),

                ) {
                Text(
                    text = if (isCopied.value) "Copied" else "Copy",
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight(600),
                    color = if (isCopied.value) {
                        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.onPrimaryContainer
                        else MaterialTheme.colorScheme.onPrimary
                    } else {
                        if (isSystemInDarkTheme()) MaterialTheme.colorScheme.inversePrimary
                        else MaterialTheme.colorScheme.primary
                    },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(BorderColor)
        )
        Text(
            text = "Share to",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onBackground
            ),
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
