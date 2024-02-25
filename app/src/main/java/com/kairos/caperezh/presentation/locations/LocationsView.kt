package com.kairos.caperezh.presentation.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.kairos.caperezh.R
import com.kairos.caperezh.common.TypeInformation
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.InformationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsView(
    navigateToDetail: (Result) -> Unit,
    viewModel: InformationViewModel = hiltViewModel()
){
    val searchQuery by viewModel.searchQuery.collectAsState()
    val myList: List<Result> = viewModel.myViewList
    LaunchedEffect(true) {
        viewModel.setTypeInformation(TypeInformation.LOCATIONS)
    }
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
        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                items(myList) { item ->
                    GridItemCardLocation(item, clickItem = {
                        navigateToDetail(item)
                    })
                }
            }
        }
    }
}

@Composable
fun GridItemCardLocation(item: Result, clickItem: (Result) -> Unit) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .padding(4.dp).clickable {
                        clickItem(item)
                    }
            ) {
                Text(text = item.name.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.type.toString())
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.dimension.toString())
            }
        }
    }
}