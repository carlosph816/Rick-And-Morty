package com.kairos.caperezh.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun ErrorView(
    clickRetry: ()-> Unit,
    message: Int,
){
    Column(
        modifier =  Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(id = message))
        Button(onClick = {
            clickRetry()
        }) {
            Text(text = "Retry")
        }
    }
}