package com.inconus.mealmanagement.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun TestScreen(testViewModel: TestViewModel) {
    val testErrorMessage by testViewModel.errorMessage.observeAsState("")
    val testStatus by testViewModel.testStatus.observeAsState("false")
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                testViewModel.pushTest()
            }
        ) {
            Text("Push Test")
        }

        if(testErrorMessage.isNotEmpty()){
            Text("test 에러 : $testErrorMessage\n")
        }

        if (testStatus==true){
            Text(" ${testViewModel.resultText} : push 성공")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewTestScreen() {
//    val viewModel = TestViewModel();
//    TestScreen(viewModel)
//}