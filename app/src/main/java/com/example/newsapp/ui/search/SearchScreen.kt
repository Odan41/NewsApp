package com.example.newsapp.ui.search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import com.example.newsapp.ui.base.State
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.data.models.response.NewsResponse
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun SearchScreen(
    searchedWord: String = "",
    onNavigateDetail: (String) -> Unit = {},
    viewModel: SearchViewModel = getViewModel{
        parametersOf(searchedWord)
    },
) {

    val context = LocalContext.current
    var inputSearch by remember { mutableStateOf("") }
    val state = viewModel.state.collectAsState()
    val news = viewModel.searchedNews.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Row{
            OutlinedTextField(
                value = inputSearch,
                onValueChange = { inputSearch = it },
                label = {
                    Text(text = "Search for article")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                )
            )
            Button(
                onClick = {
                }
            ){
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                )
            }

        }

    }


    news.value?.let {
        SearchedNewsView(
            news = it.articles,
            state = state.value,
            onNavigateDetail = onNavigateDetail,
        )}

}

@Composable
fun SearchedNewsView(
    news: List<NewsResponse.Article> = emptyList(),
    state: State = State.None,
    onNavigateDetail: (String) -> Unit = {},
){

    Box(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),
        contentAlignment = Alignment.Center
    ){
        when(state){
            State.None, State.Loading -> {
                CircularProgressIndicator()
            }
            is State.Failure ->{
                Button(onClick = { state.repeat()}){
                    Text(text = state.error.message.toString())
                }
            }
            is State.Success->{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(all = 8.dp),
                ){
                    items(news){ article ->
                        Card{
                            Row(
                                modifier = Modifier.clickable {
                                    onNavigateDetail(article.title)
                                }
                                    .padding(16.dp)
                            ){
                                Image(
                                    painter = rememberAsyncImagePainter(article.urlToImage),
                                    contentDescription = null,
                                    modifier = Modifier.size(150.dp)
                                )

                                Column(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(
                                        text = article.title ?: "-",
                                        style = MaterialTheme.typography.h6,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = article.publishedAt ?: "-",
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}


