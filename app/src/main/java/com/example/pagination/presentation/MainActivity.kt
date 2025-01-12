package com.example.pagination.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import com.example.pagination.ui.theme.PaginationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var query by rememberSaveable { mutableStateOf("") }
            val viewModel = hiltViewModel<ImageViewModel>()
            PaginationTheme {
                Scaffold(
                    topBar = {
                        TextField(value = query, onValueChange = {
                            query = it
                            viewModel.updateQuery(query)
                        }, modifier = Modifier.fillMaxWidth())
                    },
                    modifier = Modifier
                        .safeContentPadding()
                        .fillMaxSize()
                ) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, viewModel: ImageViewModel){

    val image = viewModel.image.collectAsLazyPagingItems()

    if (image.loadState.refresh is LoadState.NotLoading){
        if (image.itemCount == 0){
            Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("No Images Found")
            }
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = modifier) {

        if (image.loadState.prepend is LoadState.Loading){
            item {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (image.loadState.prepend is LoadState.Error){
            item {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(modifier = modifier.fillMaxWidth(), onClick = {
                        image.retry()
                    }) {
                        Text("Retry")
                    }
                }
            }
        }

        if (image.loadState.refresh is LoadState.NotLoading){
            if (image.itemCount != 0){
                items(
                    count = image.itemCount,
                    key = image.itemKey { it.uuId },
                    contentType = image.itemContentType { "contentType" },
                ){index ->
                    AsyncImage(
                        model = image.get(index = index)?.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(2.dp)
                    )
                }
            }
        }


        if (image.loadState.append is LoadState.Loading){
            item {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
        }

        if (image.loadState.append is LoadState.Error){
            item {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(modifier = modifier.fillMaxWidth(), onClick = {
                        image.retry()
                    }) {
                        Text("Retry")
                    }
                }
            }
        }
    }
}
