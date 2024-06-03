package com.inconus.mealmanagement.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.inconus.mealmanagement.R
import com.inconus.mealmanagement.ui.theme.MealManagementTheme

@Composable
fun PermissionDeniedDialog(
    showDialog: MutableState<Boolean>,
    onDismiss: () -> Unit,
    onGoToSettings: () -> Unit
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                modifier = Modifier
                    .width(360.dp)
                    //.padding(20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                Column(
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(id = R.string.permission_dialog_title),
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(id = R.string.permission_dialog_contents1))
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline
                                )
                            ) {
                                append(stringResource(id = R.string.permission_dialog_contents2))
                            }
                            append(stringResource(id = R.string.permission_dialog_contents3))
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Row(Modifier.height(50.dp)) {
                    Button(
                        onClick = { onDismiss() },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.5f),
                        shape = RoundedCornerShape(0.dp,0.dp,0.dp,10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E8E8))
                    ) {
                        Text(stringResource(id = R.string.close), style = MaterialTheme.typography.labelMedium, color = Color.Black)
                    }
                    Button(
                        onClick = { onGoToSettings() },
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.5f),
                        shape = RoundedCornerShape(0.dp,0.dp,10.dp,0.dp)
                    ) {
                        Text(stringResource(id = R.string.setting),style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun PreviewPermissionDialog() {
    MealManagementTheme {
        val showDialog = mutableStateOf(true)
        PermissionDeniedDialog(showDialog, {}) {}
    }
}
