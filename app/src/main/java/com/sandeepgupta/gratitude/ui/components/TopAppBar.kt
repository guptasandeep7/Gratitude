package com.sandeepgupta.gratitude.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.sandeepgupta.gratitude.util.localDateToText
import com.sandeepgupta.gratitude.viewmodel.CardViewModel
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(viewModel: CardViewModel, scrollBehavior: TopAppBarScrollBehavior) {

    val date = viewModel.date.collectAsState().value

    CenterAlignedTopAppBar(
        navigationIcon = {
            AnimatedVisibility(visible = ChronoUnit.DAYS.between(date, LocalDate.now()) < 7) {
                IconButton(
                    onClick = { viewModel.previousDay() },
                ) {
                    Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back Icon")
                }
            }
        },

        title = { Text(text = localDateToText(date)) },

        actions = {
            AnimatedVisibility(date < LocalDate.now()) {
                IconButton(
                    onClick = { viewModel.nextDay() }
                ) {
                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "Next Icon")
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}




