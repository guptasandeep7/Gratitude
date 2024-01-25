package com.sandeepgupta.gratitude.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeepgupta.gratitude.model.SocialMediaModel

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
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(top = 4.dp),
        )
    }

}