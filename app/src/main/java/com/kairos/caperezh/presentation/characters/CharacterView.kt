package com.kairos.caperezh.presentation.characters

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
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import com.kairos.caperezh.R
import com.kairos.caperezh.common.Filter
import com.kairos.caperezh.common.Gender
import com.kairos.caperezh.common.Status
import com.kairos.caperezh.common.TypeInformation
import com.kairos.caperezh.data.response.Result
import com.kairos.caperezh.presentation.ErrorView
import com.kairos.caperezh.presentation.InformationViewModel
import com.kairos.caperezh.presentation.Pages
import com.kairos.caperezh.presentation.ViewState

@Preview
@Composable
fun myFun() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_page_before),
            contentDescription = "back"
        )
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "0"
            )
            Text(text = "...")
            Text(text = "2")
        }
        Image(
            painter = painterResource(id = R.drawable.ic_page_after),
            contentDescription = "next"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterView(
    navigateToDetail: (Result) -> Unit,
    viewModel: InformationViewModel = hiltViewModel(),
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    val viewState by viewModel.viewState.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val myList: List<Result> = viewModel.myViewList
    LaunchedEffect(true) {
        viewModel.setTypeInformation(TypeInformation.CHARACTERS)
    }
    Column {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.app_name)
                )
            },
            actions = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(imageVector = Icons.Filled.MoreVert, contentDescription = "Ver más")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("All") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Filter.ALL)
                        }
                    )
                    Text(text = "Status")
                    DropdownMenuItem(
                        text = { Text("Alive") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Status.ALIVE)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Dead") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Status.DEAD)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Unknown") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Status.UNKNOWN)
                        }
                    )
                    Text(text = "Gender")
                    DropdownMenuItem(
                        text = { Text("Male") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Gender.MALE)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Female") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Gender.FEMALE)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Genderless") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Gender.GENDERLESS)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Unknown") },
                        onClick = {
                            expanded = !expanded
                            viewModel.getCharactersFilter(Gender.UNKNOWN)
                        }
                    )
                }
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
                                        viewModel.getInformationByPage(prev, Pages.CHARACTERS)
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
                                        viewModel.getInformationByPage(next, Pages.CHARACTERS)
                                    }
                                },
                                painter = painterResource(id = R.drawable.ic_page_after),
                                contentDescription = "next"
                            )
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                        ) {
                            items(myList) { item ->
                                GridItemCardCharacter(item, clickItem = {
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
fun GridItemCardCharacter(item: Result, clickItem: (Result) -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
        ) {

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(125.dp)
                    .clickable {
                        clickItem(item)
                    },
                model = item.image,
                contentDescription = "Translated description of what the image contains"
            )
            Text(text = item.status.toString())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.name.toString(), style = TextStyle(fontWeight = FontWeight.Bold))
        }

    }
}
