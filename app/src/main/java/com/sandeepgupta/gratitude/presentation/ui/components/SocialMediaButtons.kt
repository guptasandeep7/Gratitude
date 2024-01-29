package com.sandeepgupta.gratitude.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sandeepgupta.gratitude.domain.model.SocialMediaModel

@Composable
fun SocialMediaButton(socialMediaModel: SocialMediaModel, onClick: () -> Unit) {
    Column(
        Modifier
            .wrapContentSize()
            .clickable(true, onClick = { onClick() }),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painterResource(socialMediaModel.drawable),
            contentDescription = socialMediaModel.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(44.dp)
        )

        Text(
            text = socialMediaModel.title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 4.dp),
        )
    }

}