package com.sandeepgupta.gratitude.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorDialog(
    errorMsg: String,
    onRetryClick: () -> Unit
) {
    var showDialog by remember { mutableStateOf(true) }

    Box(Modifier.fillMaxSize()) {
        if (showDialog) {
            AlertDialog(
                shape = RoundedCornerShape(20.dp),
                onDismissRequest = {},//Do not close the dialog box on clicking outside
                text = {
                    Text(errorMsg)
                },
                confirmButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onRetryClick()
                    }) {
                        Text("Retry")
                    }
                },
            )
        }
    }

}