package com.sandeepgupta.gratitude.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.painterResource
import com.sandeepgupta.gratitude.R
import com.sandeepgupta.gratitude.util.localDateToText
import com.sandeepgupta.gratitude.presentation.viewmodel.CardViewModel
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
                    Icon(
                        painterResource(id = R.drawable.ic_back_arrow),
                        contentDescription = "Back Icon"
                    )
                }
            }
        },

        title = {
            Text(
                text = localDateToText(date),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        },

        actions = {
            AnimatedVisibility(date < LocalDate.now()) {
                IconButton(
                    onClick = { viewModel.nextDay() }
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_right_arrow),
                        contentDescription = "Next Icon"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        )
    )
}




