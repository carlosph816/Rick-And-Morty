package com.kairos.caperezh.presentation.episodes

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kairos.caperezh.R
import com.kairos.caperezh.common.TypeInformation
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.ErrorView
import com.kairos.caperezh.presentation.InformationViewModel
import com.kairos.caperezh.presentation.Pages
import com.kairos.caperezh.presentation.ViewState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesView(
    navigateToDetail: (Result) -> Unit,
    viewModel: InformationViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val viewState by viewModel.viewState.collectAsState()
    val myList: List<Result> = viewModel.myViewList
    LaunchedEffect(true) {
        viewModel.setTypeInformation(TypeInformation.EPISODES)
    }
    Column {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.app_name))
            }
        )
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
            }
            when (viewState) {
                is ViewState.Error -> {
                    ErrorView(clickRetry = {
                        //viewModel.setTypeInformation(TypeInformation.CHARACTERS)
                    }, message = (viewState as ViewState.Error).message)
                }

                is ViewState.Success -> {
                    val (prev, next) = viewModel.getPaginationNumber()
                    Column(modifier = Modifier.fillMaxSize()) {
                        TextField(
                            value = searchQuery,
                            onValueChange = { viewModel.setSearchQuery(it) },
                            label = { Text(stringResource(R.string.search)) },
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                modifier = Modifier.clickable {
                                    prev?.let {
                                        viewModel.getInformationByPage(prev, Pages.EPISODES)
                                    }
                                },
                                painter = painterResource(id = R.drawable.ic_page_before),
                                contentDescription = "back"
                            )
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = prev.toString()
                                )
                                Text(text = "...")
                                Text(
                                    text = next.toString()
                                )
                            }
                            Image(
                                modifier = Modifier.clickable {
                                    next?.let {
                                        viewModel.getInformationByPage(next, Pages.EPISODES)
                                    }
                                },
                                painter = painterResource(id = R.drawable.ic_page_after),
                                contentDescription = "next"
                            )
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(1),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            items(myList) { item ->
                                GridItemCardEpisodes(item, clickItem = {
                                    navigateToDetail(item)
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GridItemCardEpisodes(item: Result, clickItem: (Result) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                clickItem(item)
            }
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(8f)) {
                    Text(
                        text = item.name as String,
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.episode.toString())
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.air_date.toString())
                }
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f),
                    painter = painterResource(id = R.drawable.ic_row_right),
                    contentDescription = "detail"
                )
            }

        }
    }
}