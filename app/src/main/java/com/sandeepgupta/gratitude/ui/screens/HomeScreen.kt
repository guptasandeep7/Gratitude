package com.sandeepgupta.gratitude.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sandeepgupta.gratitude.model.CardModel
import com.sandeepgupta.gratitude.ui.components.CardUI
import com.sandeepgupta.gratitude.ui.components.ErrorDialog
import com.sandeepgupta.gratitude.ui.components.ListEndCard
import com.sandeepgupta.gratitude.ui.components.ShareBottomSheet
import com.sandeepgupta.gratitude.ui.components.TopBar
import com.sandeepgupta.gratitude.util.ApiState
import com.sandeepgupta.gratitude.viewmodel.CardViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: CardViewModel = hiltViewModel()) {

    val cardList = viewModel.cardList.collectAsState()
    val isOnline = viewModel.isOnline.collectAsState(true)
    val state = rememberModalBottomSheetState(Hidden, skipHalfExpanded = true)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var selectedCard by remember {
        mutableStateOf<CardModel?>(null)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    ModalBottomSheetLayout(
        sheetState = state,
        sheetContent = {
            selectedCard?.let {
                ShareBottomSheet(it, context,
                    onCloseClick = {
                        scope.launch { state.hide() }
                    },
                    onCopyClick = {
                        val clipboardManager =
                            context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData: ClipData = ClipData.newPlainText(
                            "text",
                            if (selectedCard!!.text == "") selectedCard!!.articleUrl
                            else "${selectedCard!!.text} ${selectedCard!!.author}"
                        )
                        clipboardManager.setPrimaryClip(clipData)
                    }
                )
            }
        },
        sheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
        scrimColor = Color.Black.copy(alpha = 0.5f),
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopBar(viewModel = viewModel, scrollBehavior)
            }
        ) {
            Column(
                Modifier.padding(it)
            ) {
                when (cardList.value) {
                    is ApiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is ApiState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                                .wrapContentHeight(),
                            verticalArrangement = Arrangement.spacedBy(32.dp)
                        ) {
                            cardList.value.data?.let { list ->
                                items(items = list, key = { it.uniqueId }) { item ->
                                    CardUI(
                                        item,
                                        LocalContext.current,
                                        onShareClick = { imageUrl, text ->
                                            selectedCard = item
                                            scope.launch {
                                                state.show()
                                            }
                                        })
                                }
                                item {
                                    ListEndCard()
                                }
                            }
                        }
                    }

                    is ApiState.Error -> {
                        ErrorDialog(
                            errorMsg = cardList.value.errorMsg ?: "Something went wrong !!!"
                        ) {
                            viewModel.fetchFromRemoteAndSave()
                        }
                    }
                }

                if (!isOnline.value) {
                    ErrorDialog(errorMsg = "No Internet !!!") {
                        viewModel.getCardListFromRoomDb()
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}