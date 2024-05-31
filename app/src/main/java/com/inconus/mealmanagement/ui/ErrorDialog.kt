package com.inconus.mealmanagement.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.ui.theme.MealManagementTheme


@Composable
fun ErrorDialog(showDialog: Boolean, errorMessage: String, onDismiss: () -> Unit) {
    if (showDialog) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.notice),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 30.dp)
                        .align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        onDismiss()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp)
                ) {
                    Text(stringResource(id = R.string.close), color = Color.White)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
    MealManagementTheme {
        val showDialog = true
        ErrorDialog(showDialog, "에러") {}
    }
}