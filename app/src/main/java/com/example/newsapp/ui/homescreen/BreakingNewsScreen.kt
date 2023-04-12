package com.example.newsapp.ui.homescreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.R

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.ui.base.State
import org.koin.androidx.compose.getViewModel


@Composable
fun BreakingNewsScreen(
    viewModel: BreakingNewsViewModel = getViewModel(),
    onNavigateDetail: (NewsResponse.Article) -> Unit = {},
){
    val news = viewModel.news.collectAsState()
    val state = viewModel.state.collectAsState()

    NewsViews(
        news = news.value.articles,
        state = state.value,
        onNavigateDetail = onNavigateDetail,
    )

}

@Composable
fun NewsViews(
    news: List<NewsResponse.Article> = emptyList(),
    state: State = State.None,
    onNavigateDetail: (NewsResponse.Article) -> Unit = {},
){
    Box(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colors.background),
        contentAlignment = Alignment.Center
    ){
        when(state){
            State.None, State.Loading -> {
                CircularProgressIndicator()
            }
            is State.Failure ->{
                Button(onClick = { state.repeat()}){
                    Text(text = "Retry")
                }
            }
            is State.Success->{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(all = 8.dp),
                ){
                    items(news){ article ->
                        Card {
                            Row(
                                modifier = Modifier.clickable {
                                    onNavigateDetail(article)
                                }
                                    .padding(16.dp)

                            ){

                                Image(
                                    painter = rememberAsyncImagePainter(article.urlToImage),
                                    contentDescription = null,
                                    modifier = Modifier.size(50.dp)
                                )
                                Column {
                                    Text(
                                        text = article.title ?: "-",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.weight(1F),
                                    )
                                    Text(
                                        text = article.description ?: "-",
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