package com.kairos.caperezh.presentation.characters

import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.InformationViewModel
import com.kairos.caperezh.presentation.episodes.GridItemCardEpisodes

@Composable
fun DetailCharacterView(
    data: Result?,
    viewModel: InformationViewModel = hiltViewModel()
){
    LaunchedEffect(true){
        viewModel.getEpisodesById(data?.episode as List<String>)
    }
    val myList: List<Result> = viewModel.myDetailEpisodesList
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
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                        .height(125.dp),
                    model = data?.image,
                    contentDescription = "Translated description of what the image contains"
                )
                Text(text = data?.status.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = data?.name.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = data?.species.toString())
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
                Text(text = "Gender", style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = data?.gender.toString(), style = TextStyle(fontWeight = FontWeight.Normal))
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )

                Text(text = "Origin", style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = data?.origin?.name.toString())
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )

                Text(text = "Location", style = TextStyle(fontWeight = FontWeight.Bold))
                Text(text = data?.location?.name.toString())
                Spacer(modifier = Modifier.height(4.dp))
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                Text(text = "Episodes", style = TextStyle(fontWeight = FontWeight.Bold))

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
                            columns = GridCells.Fixed(1),
                            modifier = Modifier
                        ) {
                            items(myList) { item ->
                                GridItemCardEpisodes(item, clickItem = {
                                    //navigateToDetail(item)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}