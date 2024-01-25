package com.sandeepgupta.gratitude.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sandeepgupta.gratitude.R

@Composable
fun ShareMoreButton(onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = { onClick() },
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                painterResource(id = R.drawable.ic_more),
                contentDescription = "More Options"
            )
        }

        Text(
            text = "More",
            style = TextStyle(
                fontSize = 11.sp,
                fontWeight = FontWeight(600),
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(top = 4.dp),
        )
    }
}