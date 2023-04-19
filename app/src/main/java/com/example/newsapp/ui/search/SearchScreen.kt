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
    viewModel: SearchViewModel = getViewModel(),
    onNavigateDetail: (String) -> Unit = {},
) {


    val inputSearch = viewModel.searchedText.collectAsState()
    val state = viewModel.state.collectAsState()
    val news = viewModel.searchedNews.collectAsState()
    val isSearching = viewModel.isSearching.collectAsState()




    SearchedNewsView(
        news = news.value?.articles ?: emptyList(),
        state = state.value,
        inputSearch = inputSearch.value,
        onNavigateDetail = onNavigateDetail,
    )

}

@Composable
fun SearchedNewsView(
    news: List<NewsResponse.Article> = emptyList(),
    state: State = State.None,
    inputSearch: String,
    viewModel: SearchViewModel = getViewModel(),
    onNavigateDetail: (String) -> Unit = {},
){

    Column(
        modifier = Modifier.fillMaxSize().background(Color.LightGray),

    ){
        Spacer(modifier = Modifier.height(20.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp).background(Color.White)){
            OutlinedTextField(
                value = inputSearch,
                onValueChange = viewModel::onSearchTextChange,
                label = {
                    Text(text = "Search for article")
                },
                modifier = Modifier.fillMaxWidth().background(Color.White).padding(8.dp),

                )
        }
        Spacer(modifier = Modifier.height(20.dp))

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


