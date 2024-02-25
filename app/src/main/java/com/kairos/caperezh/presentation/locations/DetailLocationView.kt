package com.kairos.caperezh.presentation.locations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.InformationViewModel
import com.kairos.caperezh.presentation.characters.GridItemCardCharacter

@Composable
fun DetailLocationView(
    data: Result?,
    viewModel: InformationViewModel = hiltViewModel()
){
    val myList: List<Result> = viewModel.myDetailCharactersList
    LaunchedEffect(true){
        viewModel.getCharactersById(data?.residents!!)
    }
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Card(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = data?.name.toString(), style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 32.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = data?.type.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ){
                Text(text = "Dimension", style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = data?.dimension.toString(), style = TextStyle(fontWeight = FontWeight.Normal))
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Text(text = "Characters", style = TextStyle(fontWeight = FontWeight.Bold))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    if (viewModel.showLoader) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(20.dp)
                        )
                    }else{
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            items(myList) { item ->
                                GridItemCardCharacter(item, clickItem = {
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
